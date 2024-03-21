package com.vacgom.backend.infrastructure.inoculation.persistence

import com.vacgom.backend.domain.inoculation.Vaccination
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface VaccinationRepository : JpaRepository<Vaccination, UUID> {
    fun findByVaccineName(diseaseName: String): Vaccination?
}
