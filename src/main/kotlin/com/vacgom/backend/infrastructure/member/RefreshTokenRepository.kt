package com.vacgom.backend.infrastructure.member

import com.vacgom.backend.domain.member.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RefreshTokenRepository : JpaRepository<RefreshToken, UUID>
