package com.vacgom.backend.application.auth

import com.vacgom.backend.application.auth.dto.MemberResponse
import com.vacgom.backend.domain.member.Member
import com.vacgom.backend.domain.member.constants.ProviderType
import com.vacgom.backend.domain.member.constants.Role
import com.vacgom.backend.infrastructure.member.MemberRepository
import jakarta.transaction.Transactional
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

    fun getAuthorizationUri(provider: String): URI {
        return authFactory.getAuthUriGenerator(provider).generate()
    }

    @Transactional
    fun login(
            providerType: String,
            code: String
    ): MemberResponse {
        val authConnector = authFactory.getAuthConnector(providerType)
        val oauthToken = authConnector.fetchOauthToken(code)
        val memberInfo = authConnector.fetchMemberInfo(oauthToken.accessToken)
        val member = findOrCreateMember(memberInfo.id, ProviderType.from(providerType))

        return MemberResponse(member.id!!, member.role)
    }

    private fun findOrCreateMember(
            kakaoProviderId: Long,
            providerType: ProviderType
    ): Member {
        val existingMember = memberRepository.findByProviderIdAndProviderType(kakaoProviderId, providerType)
        return existingMember ?: run {
            val newMember = Member(kakaoProviderId, providerType, Role.ROLE_TEMP_USER)
            memberRepository.save(newMember)
        }
    }
}
