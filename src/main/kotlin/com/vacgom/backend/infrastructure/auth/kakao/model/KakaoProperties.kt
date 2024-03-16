package com.vacgom.backend.infrastructure.auth.kakao.model

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class KakaoProperties(
        @Value("\${spring.security.oauth2.client.registration.kakao.client-id}") var clientId: String? = null,
        @Value("\${spring.security.oauth2.client.registration.kakao.client-secret}") var clientSecret: String? = null,
        @Value("\${spring.security.oauth2.client.registration.kakao.redirect-uri}") var redirectUri: String? = null,
        @Value("\${spring.security.oauth2.client.provider.kakao.authorization-endpoint}") var authorizationEndpoint: String? = null,
        @Value("\${spring.security.oauth2.client.provider.kakao.token-endpoint}") var tokenEndpoint: String? = null,
        @Value("\${spring.security.oauth2.client.provider.kakao.user-info-endpoint}") var userinfoEndpoint: String? = null
)
