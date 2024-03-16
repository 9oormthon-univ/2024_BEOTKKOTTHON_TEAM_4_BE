package com.vacgom.backend.domain.auth

import com.vacgom.backend.domain.auth.constants.ProviderType
import java.net.URI

interface AuthUriGenerator {
    fun isSupported(provider: ProviderType): Boolean
    fun generate(): URI
}
