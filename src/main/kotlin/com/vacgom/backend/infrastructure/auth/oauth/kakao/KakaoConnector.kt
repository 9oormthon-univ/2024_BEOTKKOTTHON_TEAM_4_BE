package com.vacgom.backend.infrastructure.auth.oauth.kakao

import com.vacgom.backend.application.auth.dto.OauthTokenResponse
import com.vacgom.backend.application.auth.dto.ResourceIdResponse
import com.vacgom.backend.domain.auth.oauth.OauthConnector
import com.vacgom.backend.domain.auth.oauth.constants.ProviderType
import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.global.security.exception.AuthError
import com.vacgom.backend.infrastructure.auth.oauth.kakao.model.KakaoProperties
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@Component
class KakaoConnector(
        private val restTemplate: RestTemplate,
        private val kakaoProperties: KakaoProperties
) : OauthConnector {
    override fun isSupported(provider: ProviderType): Boolean {
        return provider.isKakao()
    }

    override fun fetchOauthToken(code: String): OauthTokenResponse {
        val headers = createHttpHeaders()
        val body = LinkedMultiValueMap<String, String>()

        body.add("grant_type", kakaoProperties.authorizationGrantType)
        body.add("client_id", kakaoProperties.clientId)
        body.add("redirect_uri", kakaoProperties.redirectUri)
        body.add("client_secret", kakaoProperties.clientSecret)
        body.add("code", code)

        val request = HttpEntity<LinkedMultiValueMap<String, String>>(body, headers)

        return try {
            restTemplate.postForObject(
                    kakaoProperties.tokenEndpoint,
                    request,
                    OauthTokenResponse::class.java
            ) ?: throw BusinessException(AuthError.KAKAO_OAUTH_ERROR)
        } catch (exception: RestClientException) {
            throw BusinessException(AuthError.KAKAO_OAUTH_ERROR)
        }
    }

    override fun fetchMemberInfo(accessToken: String): ResourceIdResponse {
        val headers = createHttpHeaders()
        headers.set("Authorization", "Bearer $accessToken")

        val request = HttpEntity<Unit>(headers)
        return runCatching {
            restTemplate.exchange(
                    kakaoProperties.userinfoEndpoint,
                    HttpMethod.GET,
                    request,
                    ResourceIdResponse::class.java
            ).body
        }.getOrNull() ?: throw BusinessException(AuthError.KAKAO_OAUTH_ERROR)
    }
}


private fun createHttpHeaders(): HttpHeaders {
    val headers = HttpHeaders()
    headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
    return headers
}
