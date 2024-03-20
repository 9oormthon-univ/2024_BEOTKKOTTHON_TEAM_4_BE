package com.vacgom.backend.application.member

import com.vacgom.backend.application.auth.dto.AuthResponse
import com.vacgom.backend.application.auth.dto.MemberResponse
import com.vacgom.backend.application.auth.dto.TokenResponse
import com.vacgom.backend.application.member.dto.request.SignUpRequest
import com.vacgom.backend.domain.auth.constants.Role
import com.vacgom.backend.domain.member.HealthProfile
import com.vacgom.backend.domain.member.VacgomId
import com.vacgom.backend.exception.member.MemberError
import com.vacgom.backend.exception.member.VacgomIdError
import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.global.security.jwt.JwtFactory
import com.vacgom.backend.infrastructure.member.persistence.HealthProfileRepository
import com.vacgom.backend.infrastructure.member.persistence.MemberRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class MemberService(
        private val memberRepository: MemberRepository,
        private val healthProfileRepository: HealthProfileRepository,
        private val jwtFactory: JwtFactory
) {
    fun validateVacgomId(id: String) {
        val vacgomId = VacgomId(id)
        if (memberRepository.existsMemberByVacgomId(vacgomId))
            throw BusinessException(VacgomIdError.DUPLICATED)
    }

    fun signUpVacgom(
            memberId: UUID,
            request: SignUpRequest
    ): AuthResponse {
        val member = memberRepository.findById(memberId).orElseThrow { BusinessException(MemberError.NOT_FOUND) }
        val vacgomId = VacgomId(request.vacgomId)

        member.memberDetails?.updateMemberInfo(request.name, request.birthday, request.sex)
        member.updateVacgomId(vacgomId)
        member.updateRole(Role.ROLE_USER)

        val healthConditions = request.healthConditions.stream()
                .map { condition ->
                    HealthProfile(member, condition)
                }.toList()
        healthProfileRepository.saveAll(healthConditions)

        val memberResponse = MemberResponse(member.id!!, member.role)
        val tokenResponse = TokenResponse(jwtFactory.createAccessToken(member))

        return AuthResponse(memberResponse, tokenResponse)
    }
}
