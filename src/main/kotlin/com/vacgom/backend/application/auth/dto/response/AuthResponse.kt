package com.vacgom.backend.application.auth.dto.response

data class AuthResponse(
        val member: MemberResponse,
        val token: TokenResponse
)
