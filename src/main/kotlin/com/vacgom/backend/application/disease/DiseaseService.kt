package com.vacgom.backend.application.disease

import com.vacgom.backend.domain.disease.Disease
import com.vacgom.backend.infrastructure.disease.persistence.DiseaseRepository
import org.springframework.stereotype.Service

@Service
class DiseaseService(
    private val diseaseRepository: DiseaseRepository,
) {
    fun findAll(): List<Disease> {
        return diseaseRepository.findAll()
    }
}
