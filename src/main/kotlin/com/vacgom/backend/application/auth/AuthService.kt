package com.vacgom.backend.application.auth

import com.vacgom.backend.application.auth.dto.AuthResponse
import com.vacgom.backend.application.auth.dto.MemberResponse
import com.vacgom.backend.application.auth.dto.TokenResponse
import com.vacgom.backend.domain.auth.constants.Role.ROLE_TEMP_USER
import com.vacgom.backend.domain.auth.oauth.constants.ProviderType
import com.vacgom.backend.domain.member.Member
import com.vacgom.backend.global.security.jwt.JwtFactory
import com.vacgom.backend.infrastructure.member.persistence.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import java.net.URI

@Component
class AuthService(
        private val authFactory: AuthFactory,
        private val jwtFactory: JwtFactory,
        private val memberRepository: MemberRepository
) {
    fun createRedirectHeaders(redirectUri: URI): HttpHeaders {
        val headers = HttpHeaders()
        headers.location = redirectUri
        return headers
    }

    fun getAuthorizationUri(provider: String): URI {
        return authFactory.getAuthUriGenerator(provider).generate()
    }

    @Transactional
    fun login(
            providerType: String,
            code: String
    ): AuthResponse {
        val authConnector = authFactory.getAuthConnector(providerType)
        val oauthToken = authConnector.fetchOauthToken(code)
        val memberInfo = authConnector.fetchMemberInfo(oauthToken.accessToken)
        val member = findOrCreateMember(memberInfo.id, ProviderType.from(providerType))

        val memberResponse = MemberResponse(member.id!!, member.role)
        val tokenResponse = TokenResponse(jwtFactory.createAccessToken(member))

        return AuthResponse(memberResponse, tokenResponse)
    }

    private fun findOrCreateMember(
            kakaoProviderId: Long,
            providerType: ProviderType
    ): Member {
        return memberRepository.findByProviderIdAndProviderType(kakaoProviderId, providerType)
                ?: memberRepository.save(Member(kakaoProviderId, providerType, ROLE_TEMP_USER))
    }
}
