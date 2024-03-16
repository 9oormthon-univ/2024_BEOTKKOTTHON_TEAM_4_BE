package com.vacgom.backend.application.auth.dto


data class TokenResponse(
        val accessToken: String,
        val refreshToken: String
)
