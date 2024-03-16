package com.vacgom.backend.global.security.exception

import com.vacgom.backend.global.exception.error.ErrorCode
import org.springframework.http.HttpStatus


enum class AuthError(
        override val message: String,
        override val status: HttpStatus,
        override val code: String
) : ErrorCode {
    BAD_EMAIL("잘못된 이메일입니다.", HttpStatus.UNAUTHORIZED, "A_001"),
    INVALID_JWT_SIGNATURE("시그니처가 유효하지 않습니다.", HttpStatus.UNAUTHORIZED, "A_002"),
    INVALID_JWT_TOKEN("토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED, "A_003"),
    EXPIRED_JWT_TOKEN("토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED, "A_004"),
    UNSUPPORTED_JWT_TOKEN("지원되지 않는 토큰입니다.", HttpStatus.UNAUTHORIZED, "A_005"),
    EMPTY_VALUE_IN_COOKIE("쿠키에 데이터가 존재하지 않습니다.", HttpStatus.UNAUTHORIZED, "A_006"),
    UNUSABLE_TOKEN("사용할 수 없는 토큰입니다. 다시 로그인해주세요.", HttpStatus.BAD_REQUEST, "A_007"),
    UNSUPPORTED_PROVIDER("지원하지 않는 로그인 클라이언트입니다.", HttpStatus.BAD_REQUEST, "A_008"),
    KAKAO_OAUTH_ERROR("카카오 서버 통신 간 오류입니다.", HttpStatus.BAD_REQUEST, "A_009");
}
