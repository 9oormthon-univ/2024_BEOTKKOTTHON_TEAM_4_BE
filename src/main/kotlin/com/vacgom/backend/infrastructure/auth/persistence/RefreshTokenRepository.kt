package com.vacgom.backend.infrastructure.auth.persistence

import com.vacgom.backend.domain.auth.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RefreshTokenRepository : JpaRepository<RefreshToken, UUID>
