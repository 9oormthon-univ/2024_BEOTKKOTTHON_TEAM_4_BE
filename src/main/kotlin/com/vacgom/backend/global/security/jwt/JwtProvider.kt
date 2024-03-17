package com.vacgom.backend.global.security.jwt

import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.global.security.exception.AuthError
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*


@Component
class JwtProvider(
        @Value("\${jwt.secret.key}") secretKey: String,
        @Value("\${jwt.access-token-validity}") private val accessTokenValidity: Long,
        @Value("\${jwt.refresh-token-validity}") private val refreshTokenValidity: Long
) {
    private val secretKey: Key

    init {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }

    fun createAccessToken(memberId: UUID): String {
        return createToken(memberId, accessTokenValidity)
    }

    fun createRefreshToken(memberId: UUID): String {
        return createToken(memberId, refreshTokenValidity)
    }

    fun getPayload(token: String): String {
        return getClaims(token).body.subject
    }

    private fun createToken(id: UUID, expireTime: Long): String {
        val now = Date()
        val expireDate = Date(now.time + expireTime)
        return Jwts.builder()
                .setSubject(id.toString())
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact()
    }

    fun validateToken(token: String) {
        getClaims(token)
    }

    private fun getClaims(token: String): Jws<Claims> {
        return try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
        } catch (exception: JwtException) {
            throw BusinessException(AuthError.EXPIRED_JWT_TOKEN)
        }
    }
}

