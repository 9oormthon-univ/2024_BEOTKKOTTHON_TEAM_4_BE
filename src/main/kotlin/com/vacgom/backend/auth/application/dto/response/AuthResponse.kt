package com.vacgom.backend.auth.application.dto.response

data class AuthResponse(
        val success: Boolean,
        val member: MemberResponse,
        val token: TokenResponse
)
