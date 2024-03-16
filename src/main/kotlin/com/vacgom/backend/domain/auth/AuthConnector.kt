package com.vacgom.backend.domain.auth

import com.vacgom.backend.application.auth.dto.OauthToken
import com.vacgom.backend.domain.member.constants.ProviderType
import com.vacgom.backend.infrastructure.auth.kakao.model.KakaoMemberInfo

interface AuthConnector {
    fun isSupported(provider: ProviderType): Boolean
    fun fetchOauthToken(code: String): OauthToken
    fun fetchMemberInfo(accessToken: String): KakaoMemberInfo
}
