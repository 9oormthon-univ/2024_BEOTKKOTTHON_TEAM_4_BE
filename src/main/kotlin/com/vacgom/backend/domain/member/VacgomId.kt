package com.vacgom.backend.domain.member

import com.vacgom.backend.exception.member.VacgomIdError
import com.vacgom.backend.global.exception.error.BusinessException
import jakarta.persistence.Embeddable

@Embeddable
class VacgomId(
        var vacgomId: String
) {
    init {
        validatePattern(vacgomId)
        validateConsecutiveUnderscores(vacgomId)
    }

    private fun validatePattern(id: String) {
        require(id.matches("[a-z0-9_]+".toRegex())) {
            throw BusinessException(VacgomIdError.INVALID_VACGOM_ID_PATTERN)
        }
    }

    private fun validateConsecutiveUnderscores(id: String) {
        require(id.matches("[a-z0-9_]+".toRegex())) {
            throw BusinessException(VacgomIdError.CONSECUTIVE_UNDERSCORES)
        }
    }
}
