package com.vacgom.backend.presentation.auth

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class KakaoProperty(
        @Value("{spring.security.oauth2.client.registration.kakao.client-id}")
        val clientId: String,

        @Value("{spring.security.oauth2.client.registration.kakao.client-secret}")
        val clientSecret: String,

        @Value("{spring.security.oauth2.client.registration.kakao.redirect-uri}")
        val redirectUri: String,

        @Value("{spring.oauth.kakao.authorization-endpoint}")
        val authorizationEndPoint: String,

        @Value("{spring.oauth.kakao.token-endpoint}")
        val tokenEndPoint: String,

        @Value("{spring.oauth.kakao.userinfo-endpoint}")
        val userInfoEndPoint: String
)
