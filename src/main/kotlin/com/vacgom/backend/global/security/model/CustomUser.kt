package com.vacgom.backend.global.security.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import java.util.*


class CustomUser(
        val id: UUID?,
        username: String,
        password: String,
        authorities: Collection<GrantedAuthority>
) : User(username, password, authorities)
