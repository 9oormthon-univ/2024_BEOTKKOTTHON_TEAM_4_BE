package com.vacgom.backend.application.member

import com.vacgom.backend.domain.disease.HealthCondition
import com.vacgom.backend.domain.member.HealthProfile
import com.vacgom.backend.domain.member.Member
import com.vacgom.backend.exception.member.MemberError
import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.infrastructure.member.persistence.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import java.util.*

@Component
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
) {
    fun findMember(id: UUID): Member? {
        return memberRepository.findById(id).orElseThrow()
    }

    fun updateHealthCondition(
        id: UUID,
        healthProfiles: List<HealthCondition>,
    ): Member {
        val member =
            memberRepository.findById(id).orElseThrow {
                BusinessException(MemberError.NOT_FOUND)
            }

        member.healthProfiles.clear()
        member.healthProfiles.addAll(healthProfiles.map { HealthProfile(member, it) })
        return memberRepository.save(member)
    }
}
