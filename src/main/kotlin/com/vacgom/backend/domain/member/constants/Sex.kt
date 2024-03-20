package com.vacgom.backend.domain.member.constants

import com.vacgom.backend.exception.member.MemberError
import com.vacgom.backend.global.exception.error.BusinessException

enum class Sex(
        val value: String
) {
    MALE("M"),
    FEMALE("F");

    companion object {
        fun getSexByValue(value: String): Sex {
            return entries.find { it.value == value }
                    ?: throw BusinessException(MemberError.INVALID_SEX_REQUESTED)
        }
    }
}
