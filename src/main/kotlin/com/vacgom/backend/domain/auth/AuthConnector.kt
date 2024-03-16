package com.vacgom.backend.domain.auth

import com.vacgom.backend.application.auth.dto.KakaoMemberResponse
import com.vacgom.backend.application.auth.dto.OauthTokenResponse
import com.vacgom.backend.domain.auth.constants.ProviderType

interface AuthConnector {
    fun isSupported(provider: ProviderType): Boolean
    fun fetchOauthToken(code: String): OauthTokenResponse
    fun fetchMemberInfo(accessToken: String): KakaoMemberResponse
}
