package com.vacgom.backend.domain.auth.oauth

import com.vacgom.backend.application.auth.dto.response.OauthTokenResponse
import com.vacgom.backend.application.auth.dto.response.ResourceIdResponse
import com.vacgom.backend.domain.auth.oauth.constants.ProviderType

interface OauthConnector {
    fun isSupported(provider: ProviderType): Boolean
    fun fetchOauthToken(code: String): OauthTokenResponse
    fun fetchMemberInfo(accessToken: String): ResourceIdResponse
}
