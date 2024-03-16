package com.vacgom.backend.domain.auth

import com.vacgom.backend.application.auth.dto.AuthTokenResponse
import com.vacgom.backend.domain.auth.model.AuthProvider

interface AuthConnector {
    fun isSupported(provider: AuthProvider): Boolean
    fun fetchAccessToken(code: String): AuthTokenResponse
    fun fetchMemberInfo(accessToken: String)
}
