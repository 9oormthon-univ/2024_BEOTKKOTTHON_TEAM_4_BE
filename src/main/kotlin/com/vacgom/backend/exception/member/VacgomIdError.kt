package com.vacgom.backend.exception.member

import com.vacgom.backend.global.exception.error.ErrorCode
import org.springframework.http.HttpStatus


internal enum class VacgomIdError(
        override val message: String,
        override val status: HttpStatus,
        override val code: String
) : ErrorCode {
    INVALID_VACGOM_ID_PATTERN("백곰 아이디는 영문 소문자와 숫자, 언더바(_)로 구성되어야 합니다.", HttpStatus.BAD_REQUEST, "VI_001"),
    CONSECUTIVE_UNDERSCORES("언더바(_)는 최대 3개 까지 연속으로 사용할 수 있습니다.", HttpStatus.BAD_REQUEST, "VI_002");
}

