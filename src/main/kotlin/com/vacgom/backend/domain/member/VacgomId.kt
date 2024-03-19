package com.vacgom.backend.domain.member

import com.vacgom.backend.exception.member.VacgomIdError
import com.vacgom.backend.global.exception.error.BusinessException
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class VacgomId(
        @Column(unique = true) var vacgomId: String
) {
    companion object {
        private const val VACGOM_ID_REGEX = "[a-z0-9_]+"
        private const val CONSECUTIVE_UNDERSCORES = "____"
        private const val MINIMUM_LENGTH = 1
        private const val MAXIMUM_LENGTH = 20
    }

    init {
        validatePattern(vacgomId)
    }

    private fun validatePattern(id: String) {
        require(id.matches(VACGOM_ID_REGEX.toRegex())) {
            throw BusinessException(VacgomIdError.INVALID_VACGOM_ID_PATTERN)
        }
        require(CONSECUTIVE_UNDERSCORES !in id) {
            throw BusinessException(VacgomIdError.CONSECUTIVE_UNDERSCORES)
        }
        require(id.length in MINIMUM_LENGTH..MAXIMUM_LENGTH) {
            throw BusinessException(VacgomIdError.INVALID_LENGTH)
        }
    }
}
