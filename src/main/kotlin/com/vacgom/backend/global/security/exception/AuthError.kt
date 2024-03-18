package com.vacgom.backend.global.security.exception

import com.vacgom.backend.global.exception.error.ErrorCode
import org.springframework.http.HttpStatus


enum class AuthError(
        override val message: String,
        override val status: HttpStatus,
        override val code: String
) : ErrorCode {
    INVALID_JWT_SIGNATURE("시그니처가 유효하지 않습니다.", HttpStatus.UNAUTHORIZED, "A_001"),
    INVALID_JWT_TOKEN("토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED, "A_002"),
    EXPIRED_JWT_TOKEN("토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED, "A_003"),
    UNSUPPORTED_JWT_TOKEN("지원되지 않는 토큰입니다.", HttpStatus.UNAUTHORIZED, "A_004"),
    UNSUPPORTED_PROVIDER("지원하지 않는 로그인 클라이언트입니다.", HttpStatus.BAD_REQUEST, "A_005"),
    KAKAO_OAUTH_ERROR("카카오 서버 통신 간 오류입니다.", HttpStatus.BAD_REQUEST, "A_006");
}
