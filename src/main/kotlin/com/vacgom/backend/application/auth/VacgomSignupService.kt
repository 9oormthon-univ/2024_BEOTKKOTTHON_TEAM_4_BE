package com.vacgom.backend.application.auth

import com.vacgom.backend.application.auth.dto.request.SignUpRequest
import com.vacgom.backend.application.auth.dto.response.AuthResponse
import com.vacgom.backend.application.auth.dto.response.MemberResponse
import com.vacgom.backend.application.auth.dto.response.TokenResponse
import com.vacgom.backend.domain.auth.constants.Role
import com.vacgom.backend.domain.inoculation.Inoculation
import com.vacgom.backend.domain.member.HealthProfile
import com.vacgom.backend.domain.member.MemberDetails
import com.vacgom.backend.domain.member.Nickname
import com.vacgom.backend.exception.inoculation.VaccineError
import com.vacgom.backend.exception.member.MemberError
import com.vacgom.backend.exception.member.NicknameError
import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.global.security.jwt.JwtFactory
import com.vacgom.backend.infrastructure.inoculation.persistence.InoculationRepository
import com.vacgom.backend.infrastructure.inoculation.persistence.VaccinationRepository
import com.vacgom.backend.infrastructure.member.persistence.HealthProfileRepository
import com.vacgom.backend.infrastructure.member.persistence.MemberRepository
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
        private val jwtFactory: JwtFactory
) {
    fun validateNickname(id: String) {
        val nickname = Nickname(id)
        if (memberRepository.existsMemberByNickname(nickname))
            throw BusinessException(NicknameError.DUPLICATED)
    }

    fun signUpVacgom(
            memberId: UUID,
            request: SignUpRequest
    ): AuthResponse {
        val (nickname, healthConditions) = request.memberInfo
        val (name, birthday, sex, vaccines) = request.vaccinationInfo

        val member = memberRepository.findById(memberId).orElseThrow { BusinessException(MemberError.NOT_FOUND) }
        val validatedNickname = Nickname(nickname)
        val memberDetails = MemberDetails(name, birthday, sex)

        member.updateNickname(validatedNickname)
        member.updateMemberDetails(memberDetails)
        member.updateRole(Role.ROLE_USER)

        val healthProfiles = healthConditions.stream()
                .map { condition ->
                    HealthProfile(member, condition)
                }.toList()
        healthProfileRepository.saveAll(healthProfiles)

        val memberResponse = MemberResponse(member.id!!, member.role)
        val tokenResponse = TokenResponse(jwtFactory.createAccessToken(member))
        val inoculations = request.vaccinationInfo.vaccineList
                .map { vaccine ->
                    val vaccination = vaccinationRepository.findByVaccineName(vaccine.vaccineType)
                            ?: throw BusinessException(VaccineError.UNKNOWN_VACCINE_REQUESTED).also { log.warn("추가되지 않은 백신: {$vaccine.vaccineType}") }
                    Inoculation(vaccine.inoculationOrder, vaccine.inoculationOrderString, vaccine.date, vaccine.agency, vaccine.vaccineName, vaccine.vaccineBrandName, vaccine.lotNumber, member, vaccination)
                }.toList()

        inoculationRepository.saveAll(inoculations)
        member.addInoculations(inoculations)
        return AuthResponse(memberResponse, tokenResponse)
    }
}
