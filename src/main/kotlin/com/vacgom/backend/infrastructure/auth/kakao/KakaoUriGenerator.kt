package com.vacgom.backend.infrastructure.auth.kakao

import com.vacgom.backend.domain.auth.AuthUriGenerator
import com.vacgom.backend.domain.auth.constants.ProviderType
import com.vacgom.backend.infrastructure.auth.kakao.model.KakaoProperties
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@Component
class KakaoUriGenerator(
        private val kakaoProperties: KakaoProperties
) : AuthUriGenerator {

    override fun isSupported(provider: ProviderType): Boolean {
        return provider.isKakao()
    }

    override fun generate(): URI {
        return UriComponentsBuilder
                .fromUriString(kakaoProperties.authorizationEndpoint!!)
                .queryParam("response_type", "code")
                .queryParam("client_id", kakaoProperties.clientId)
                .queryParam("redirect_uri", kakaoProperties.redirectUri)
                .build()
                .toUri()
    }
}
