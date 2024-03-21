package com.vacgom.backend.application.member

import com.vacgom.backend.domain.member.Member
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
}
