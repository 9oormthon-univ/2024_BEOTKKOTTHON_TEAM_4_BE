package com.vacgom.backend.application.auth

import com.vacgom.backend.domain.auth.AuthConnector
import com.vacgom.backend.domain.auth.AuthUriGenerator
import com.vacgom.backend.domain.auth.model.AuthProvider
import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.global.security.exception.AuthError
import org.springframework.stereotype.Service
import java.net.URI

@Service
class AuthFactory(
        private val connectors: List<AuthConnector>,
        private val uriProviders: List<AuthUriGenerator>
) {
    fun getAuthConnector(provider: String): AuthConnector {
        val authProvider = AuthProvider.from(provider)

        return connectors.firstOrNull {
            it.isSupported(authProvider)
        } ?: throw BusinessException(AuthError.UNSUPPORTED_PROVIDER)
    }

    fun getAuthorizationUri(provider: String): URI {
        val authProvider = AuthProvider.from(provider)

        return uriProviders.stream()
                .filter { uriProvider -> uriProvider.isSupported(authProvider) }
                .findFirst()
                .orElseThrow { BusinessException(AuthError.UNSUPPORTED_PROVIDER) }
                .generate()
    }
}
