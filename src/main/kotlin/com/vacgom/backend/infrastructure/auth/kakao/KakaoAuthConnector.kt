package com.vacgom.backend.infrastructure.auth.kakao

import com.vacgom.backend.application.auth.dto.KakaoMemberResponse
import com.vacgom.backend.application.auth.dto.OauthTokenResponse
import com.vacgom.backend.domain.auth.AuthConnector
import com.vacgom.backend.domain.auth.constants.ProviderType
import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.global.security.exception.AuthError
import com.vacgom.backend.infrastructure.auth.kakao.model.KakaoProperties
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
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
    override fun isSupported(provider: ProviderType): Boolean {
        return provider.isKakao()
    }

    override fun fetchOauthToken(code: String): OauthTokenResponse {
        val headers = createHttpHeaders()
        val body: MultiValueMap<String, String> = LinkedMultiValueMap()

        body.add("grant_type", "authorization_code")
        body.add("client_id", kakaoProperties.clientId)
        body.add("redirect_uri", kakaoProperties.redirectUri)
        body.add("code", code)
        body.add("client_secret", kakaoProperties.clientSecret)

        val request = HttpEntity<MultiValueMap<String, String>>(body, headers)
        return try {
            restTemplate.postForObject(
                    kakaoProperties.tokenEndpoint!!,
                    request,
                    OauthTokenResponse::class.java
            )!!
        } catch (exception: RestClientException) {
            throw BusinessException(AuthError.KAKAO_OAUTH_ERROR)
        }
    }

    override fun fetchMemberInfo(accessToken: String): KakaoMemberResponse {
        val headers = createHttpHeaders()
        headers.set("Authorization", "Bearer $accessToken")

        val request: HttpEntity<*> = HttpEntity<Any>(headers)
        return try {
            restTemplate.exchange(
                    kakaoProperties.userinfoEndpoint!!,
                    HttpMethod.GET,
                    request,
                    KakaoMemberResponse::class.java
            ).body ?: throw BusinessException(AuthError.KAKAO_OAUTH_ERROR)
        } catch (exception: RestClientException) {
            println("e = ${exception}")
            throw BusinessException(AuthError.KAKAO_OAUTH_ERROR)
        }
    }


    private fun createHttpHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        return headers
    }
}
