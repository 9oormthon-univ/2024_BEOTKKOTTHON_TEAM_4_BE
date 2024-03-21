package com.vacgom.backend.infrastructure.inoculation.persistence

import com.vacgom.backend.domain.inoculation.Inoculation
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface InoculationRepository : JpaRepository<Inoculation, UUID>
