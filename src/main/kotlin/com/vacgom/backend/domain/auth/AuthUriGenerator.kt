package com.vacgom.backend.domain.auth

import com.vacgom.backend.domain.auth.model.AuthProvider
import java.net.URI

interface AuthUriGenerator {
    fun isSupported(provider: AuthProvider): Boolean
    fun generate(): URI
}
