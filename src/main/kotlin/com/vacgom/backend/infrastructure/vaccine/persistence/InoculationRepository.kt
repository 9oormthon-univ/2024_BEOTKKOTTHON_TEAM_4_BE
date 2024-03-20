package com.vacgom.backend.infrastructure.vaccine.persistence

import com.vacgom.backend.domain.vaccine.Inoculation
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface InoculationRepository : JpaRepository<Inoculation, UUID>
