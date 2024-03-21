package com.vacgom.backend.infrastructure.disease.persistence

import com.vacgom.backend.domain.disease.Disease
import org.springframework.data.jpa.repository.JpaRepository

interface DiseaseRepository : JpaRepository<Disease, Long>
