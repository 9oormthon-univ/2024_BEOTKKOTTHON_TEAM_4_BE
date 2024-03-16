package com.vacgom.backend.infrastructure.auth.kakao

import com.vacgom.backend.application.auth.dto.AuthTokenResponse
import com.vacgom.backend.application.auth.property.KakaoProperties
import com.vacgom.backend.domain.auth.AuthConnector
import com.vacgom.backend.domain.auth.model.AuthProvider
import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.global.security.exception.AuthError
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@Component
class KakaoAuthConnector(
        private val restTemplate: RestTemplate,
        private val kakaoProperties: KakaoProperties
) : AuthConnector {
    companion object {
        val GRANT_TYPE: String = "authorization_code"
        val BEARER: String = "Bearer "
    }

    override fun isSupported(provider: AuthProvider): Boolean {
        return provider.isKakao()
    }

    override fun fetchAccessToken(code: String): AuthTokenResponse {
        val headers = createHttpHeaders()
        val body: MultiValueMap<String, String> = LinkedMultiValueMap()

        body.add("grant_type", GRANT_TYPE)
        body.add("client_id", kakaoProperties.clientId)
        body.add("client_secret", kakaoProperties.clientSecret)
        body.add("redirect_uri", kakaoProperties.redirectUri)
        body.add("code", code)

        val request = HttpEntity<MultiValueMap<String, String>>(body, headers)
        return try {
            restTemplate.postForObject(
                    kakaoProperties.tokenEndpoint!!,
                    request,
                    AuthTokenResponse::class.java
            )!!
        } catch (exception: RestClientException) {
            throw BusinessException(AuthError.KAKAO_OAUTH_ERROR)
        }
    }

    override fun fetchMemberInfo(accessToken: String) {
        TODO("Not yet implemented")
    }

    private fun createHttpHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        return headers
    }
}
