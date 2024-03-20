package com.vacgom.backend.application.auth

import com.vacgom.backend.application.auth.dto.request.SignUpRequest
import com.vacgom.backend.application.auth.dto.response.AuthResponse
import com.vacgom.backend.application.auth.dto.response.MemberResponse
import com.vacgom.backend.application.auth.dto.response.TokenResponse
import com.vacgom.backend.domain.auth.constants.Role
import com.vacgom.backend.domain.member.HealthProfile
import com.vacgom.backend.domain.member.MemberDetails
import com.vacgom.backend.domain.member.Nickname
import com.vacgom.backend.domain.vaccine.Inoculation
import com.vacgom.backend.domain.vaccine.constants.Vaccination
import com.vacgom.backend.exception.member.MemberError
import com.vacgom.backend.exception.member.NicknameError
import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.global.security.jwt.JwtFactory
import com.vacgom.backend.infrastructure.member.persistence.HealthProfileRepository
import com.vacgom.backend.infrastructure.member.persistence.MemberRepository
import com.vacgom.backend.infrastructure.vaccine.persistence.InoculationRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class VacgomSignupService(
        private val memberRepository: MemberRepository,
        private val healthProfileRepository: HealthProfileRepository,
        private val inoculationRepository: InoculationRepository,
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

        val inoculations = vaccines.stream()
                .map { vaccine ->
                    val (vaccineType, inoculationOrder, inoculationOrderString, date, agency, vaccineName, vaccineBrandName, lotNumber)
                            = vaccine
                    val vaccination = Vaccination.getVaccinationByName(vaccineType)
                    Inoculation(vaccination, inoculationOrder, inoculationOrderString, date, agency, vaccineName, vaccineBrandName, lotNumber, member)
                }.toList()

        inoculationRepository.saveAll(inoculations)
        member.addInoculations(inoculations)

        return AuthResponse(memberResponse, tokenResponse)
    }
}
