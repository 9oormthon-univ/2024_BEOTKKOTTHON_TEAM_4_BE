package com.vacgom.backend.application.auth

import com.vacgom.backend.infrastructure.member.MemberRepository
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import java.net.URI

@Service
class AuthService(
        private val authFactory: AuthFactory,
        private val memberRepository: MemberRepository
) {
    fun createRedirectHeaders(redirectUri: URI): HttpHeaders {
        val headers = HttpHeaders()
        headers.location = redirectUri
        return headers
    }
}
