package com.vacgom.backend.application.auth.dto

data class AuthResponse(
        val member: MemberResponse,
        val token: TokenResponse
)
