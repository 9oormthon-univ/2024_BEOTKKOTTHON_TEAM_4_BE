package com.vacgom.backend.disease.domain.constants

enum class AgeCondition(
        val title: String,
        val value: Int,
) {
    ALL("전체", 0b000000),
    AGE19TO29("만 19-29세", 0b100000),
    AGE30TO39("만 30-39세", 0b010000),
    AGE40TO49("만 40-49세", 0b001000),
    AGE50TO59("만 50-59세", 0b000100),
    AGE60TO64("만 60-64세", 0b000010),
    AGEOVER65("만 65세 이상", 0b000001);

    companion object {
        fun getAgeCondition(value: Int): AgeCondition {
            return when {
                value in 19..29 -> AGE19TO29
                value in 30..39 -> AGE30TO39
                value in 40..49 -> AGE40TO49
                value in 50..59 -> AGE50TO59
                value in 60..64 -> AGE60TO64
                value >= 65 -> AGEOVER65
                else -> AGE19TO29
            }
        }
    }
}
