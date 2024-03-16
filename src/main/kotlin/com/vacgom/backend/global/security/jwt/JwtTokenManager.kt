package com.vacgom.backend.global.security.jwt

import com.vacgom.backend.application.auth.dto.TokenResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtTokenManager(
        private val jwtProvider: JwtProvider
) {
    fun generate(memberId: UUID): TokenResponse {
        val accessToken: String = jwtProvider.createAccessToken(memberId)
        val refreshToken: String = jwtProvider.createRefreshToken(memberId)
        return TokenResponse(accessToken, refreshToken)
    }

    fun getId(token: String): UUID {
        return UUID.fromString(jwtProvider.getPayload(token))
    }

    fun validateToken(token: String) {
        jwtProvider.validateToken(token)
    }
}
