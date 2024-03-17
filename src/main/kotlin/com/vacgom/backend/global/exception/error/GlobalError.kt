package com.vacgom.backend.global.exception.error

import org.springframework.http.HttpStatus

enum class GlobalError(
        override val message: String,
        override val status: HttpStatus,
        override val code: String
) : ErrorCode {
    GLOBAL_NOT_FOUND("리소스가 존재하지 않습니다.", HttpStatus.NOT_FOUND, "G_001"),
    INVALID_REQUEST_PARAM("요청 파라미터가 유효하지 않습니다.", HttpStatus.BAD_REQUEST, "G_002");
}
