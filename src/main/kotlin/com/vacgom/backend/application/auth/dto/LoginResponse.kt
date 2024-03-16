package com.vacgom.backend.application.auth.dto

data class LoginResponse(
        val member: MemberResponse,
        val token: TokenResponse
)
