package com.vacgom.backend.infrastructure.member.persistence

import com.vacgom.backend.domain.member.HealthProfile
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface HealthProfileRepository : JpaRepository<HealthProfile, UUID>
