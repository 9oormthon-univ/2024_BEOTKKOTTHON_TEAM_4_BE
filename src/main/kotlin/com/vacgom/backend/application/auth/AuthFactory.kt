package com.vacgom.backend.application.auth

import com.vacgom.backend.domain.auth.AuthConnector
import com.vacgom.backend.domain.auth.AuthUriGenerator
import com.vacgom.backend.domain.auth.constants.ProviderType
import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.global.security.exception.AuthError
import org.springframework.stereotype.Component

@Component
class AuthFactory(
        private val connectors: List<AuthConnector>,
        private val uriProviders: List<AuthUriGenerator>
) {
    fun getAuthConnector(provider: String): AuthConnector {
        val providerType = ProviderType.from(provider)

        return connectors.firstOrNull {
            it.isSupported(providerType)
        } ?: throw BusinessException(AuthError.UNSUPPORTED_PROVIDER)
    }

    fun getAuthUriGenerator(provider: String): AuthUriGenerator {
        val providerType = ProviderType.from(provider)

        return uriProviders.firstOrNull {
            it.isSupported(providerType)
        } ?: throw BusinessException(AuthError.UNSUPPORTED_PROVIDER)
    }
}
