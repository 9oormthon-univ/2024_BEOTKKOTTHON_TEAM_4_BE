package com.vacgom.backend.domain.auth.oauth

import com.vacgom.backend.domain.auth.oauth.constants.ProviderType
import java.net.URI

interface OauthUriGenerator {
    fun isSupported(provider: ProviderType): Boolean
    fun generate(): URI
}
