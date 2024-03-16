package com.vacgom.backend.domain.auth.model

import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.global.security.exception.AuthError

enum class AuthProvider(val provider: String) {
    KAKAO("kakao");

    companion object {
        fun from(provider: String): AuthProvider {
            return entries.firstOrNull { it.provider == provider }
                    ?: throw BusinessException(AuthError.UNSUPPORTED_PROVIDER)
        }
    }

    fun isKakao(): Boolean {
        return this == KAKAO
    }
}
