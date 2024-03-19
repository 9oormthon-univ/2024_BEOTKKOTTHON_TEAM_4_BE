package com.vacgom.backend.application.member

import com.vacgom.backend.domain.member.VacgomId
import com.vacgom.backend.exception.member.VacgomIdError
import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.infrastructure.member.persistence.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
        private val memberRepository: MemberRepository
) {
    fun validateVacgomId(id: String) {
        val vacgomId = VacgomId(id)
        if (memberRepository.existsMemberByVacgomId(vacgomId))
            throw BusinessException(VacgomIdError.DUPLICATED)
    }
}
