package com.vacgom.backend.domain.auth.constants

enum class Role {
    ROLE_GUEST,
    ROLE_TEMP_USER,
    ROLE_USER;

    fun isGuest(role: Role): Boolean {
        return role == ROLE_GUEST
    }

    fun isTempUser(role: Role): Boolean {
        return role == ROLE_TEMP_USER
    }

    fun isUser(role: Role): Boolean {
        return role == ROLE_USER
    }
}
