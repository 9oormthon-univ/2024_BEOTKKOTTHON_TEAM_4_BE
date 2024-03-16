package com.vacgom.backend.application.auth.dto

data class AuthTokenResponse(
        val accessToken: String,
        val refreshToken: String
)
