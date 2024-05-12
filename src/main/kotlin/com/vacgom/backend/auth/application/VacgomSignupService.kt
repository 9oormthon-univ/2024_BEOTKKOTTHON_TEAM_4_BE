package com.vacgom.backend.auth.application

import com.vacgom.backend.auth.application.dto.request.SignUpRequest
import com.vacgom.backend.auth.application.dto.response.AuthResponse
import com.vacgom.backend.auth.application.dto.response.MemberResponse
import com.vacgom.backend.auth.application.dto.response.TokenResponse
import com.vacgom.backend.auth.domain.constants.Role
import com.vacgom.backend.disease.domain.constants.AgeCondition
import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.global.security.jwt.JwtFactory
import com.vacgom.backend.inoculation.domain.Inoculation
import com.vacgom.backend.inoculation.infrastructure.persistence.InoculationRepository
import com.vacgom.backend.inoculation.infrastructure.persistence.VaccinationRepository
import com.vacgom.backend.member.domain.HealthProfile
import com.vacgom.backend.member.domain.MemberDetails
import com.vacgom.backend.member.domain.Nickname
import com.vacgom.backend.member.exception.MemberError
import com.vacgom.backend.member.exception.NicknameError
import com.vacgom.backend.member.infrastructure.persistence.HealthProfileRepository
import com.vacgom.backend.member.infrastructure.persistence.MemberRepository
import com.vacgom.backend.notification.application.NotificationService
import com.vacgom.backend.search.application.SearchService
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class VacgomSignupService(
    private val memberRepository: MemberRepository,
    private val healthProfileRepository: HealthProfileRepository,
    private val inoculationRepository: InoculationRepository,
    private val vaccinationRepository: VaccinationRepository,
    private val log: Logger,
    private val jwtFactory: JwtFactory,
    private val notificationService: NotificationService,
    private val searchService: SearchService,
) {
    fun validateNickname(id: String) {
        val nickname = Nickname(id)
        if (memberRepository.existsMemberByNickname(nickname)) {
            throw BusinessException(NicknameError.DUPLICATED)
        }
    }

    fun signUpVacgom(
        memberId: UUID,
        request: SignUpRequest,
    ): AuthResponse? {
        val (nickname, healthConditions) = request.memberInfo
        val (name, birthday, sex, vaccines) = request.vaccinationInfo

        val member = memberRepository.findById(memberId).orElseThrow { BusinessException(MemberError.NOT_FOUND) }
        val validatedNickname = Nickname(nickname)
        val memberDetails = MemberDetails(name, birthday, sex)

        member.updateNickname(validatedNickname)
        member.updateMemberDetails(memberDetails)
        member.updateRole(Role.ROLE_USER)

        val healthProfiles =
            healthConditions.stream()
                .map { condition ->
                    HealthProfile(member, condition)
                }.toList()
        healthProfileRepository.saveAll(healthProfiles)

        val memberResponse = MemberResponse(member.id!!, member.role)
        val tokenResponse = TokenResponse(jwtFactory.createAccessToken(member))
        val inoculations =
            request.vaccinationInfo.vaccineList.mapNotNull { vaccine ->
                val vaccination = vaccinationRepository.findByVaccineName(vaccine.vaccineType)
                if (vaccination != null) {
                    Inoculation(
                        vaccine.inoculationOrder,
                        vaccine.inoculationOrderString,
                        vaccine.date,
                        vaccine.agency,
                        vaccine.vaccineName,
                        vaccine.vaccineBrandName,
                        vaccine.lotNumber,
                        member,
                        vaccination,
                    )
                } else {
                    log.warn("추가해야 하는 백신 : {} {} {}", vaccine.vaccineType, vaccine, vaccine.vaccineName)
                    null
                }
            }.toList()

        inoculationRepository.saveAll(inoculations)
        member.addInoculations(inoculations)

        val age = Calendar.getInstance().get(Calendar.YEAR) - memberDetails.birthday.year
        val ageCondition =
            when (age) {
                in 19..29 -> AgeCondition.AGE19TO29
                in 30..39 -> AgeCondition.AGE30TO39
                in 40..49 -> AgeCondition.AGE40TO49
                in 50..59 -> AgeCondition.AGE50TO59
                in 60..69 -> AgeCondition.AGE60TO64
                else -> AgeCondition.AGEOVER65
            }

        val recommendedDiseases =
            searchService.searchDisease(
                listOf(ageCondition),
                healthConditions,
            )

        notificationService.sendNotification(
            memberId,
            "${member.nickname}님을 위한 백신이 ${recommendedDiseases.size}개 있어요!",
            "vaccine",
        )

        notificationService.sendNotification(
            memberId,
            "${member.nickname}님의 접종 인증서가 ${inoculations.size}개 발급됐어요",
            "vaccine",
        )

        notificationService.sendNotification(
            memberId,
            "${member.nickname}님! 백곰 가입을 환영해요 :)",
            "success",
        )
        return AuthResponse(true, memberResponse, tokenResponse)
    }
}
