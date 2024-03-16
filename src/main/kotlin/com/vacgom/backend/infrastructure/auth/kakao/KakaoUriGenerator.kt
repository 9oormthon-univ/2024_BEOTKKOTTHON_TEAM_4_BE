package com.vacgom.backend.infrastructure.auth.kakao

import com.vacgom.backend.domain.auth.AuthUriGenerator
import com.vacgom.backend.domain.auth.model.AuthProvider
import com.vacgom.backend.presentation.auth.KakaoProperty
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

class KakaoUriGenerator(
        private val property: KakaoProperty
) : AuthUriGenerator {

    override fun isSupported(provider: AuthProvider): Boolean {
        return provider.isKakao()
    }

    override fun generate(): URI {
        return UriComponentsBuilder
                .fromUriString(property.authorizationEndPoint)
                .queryParam("response_type", "code")
                .queryParam("client_id", property.clientId)
                .queryParam("redirect_uri", property.redirectUri)
                .build()
                .toUri()
    }

}
