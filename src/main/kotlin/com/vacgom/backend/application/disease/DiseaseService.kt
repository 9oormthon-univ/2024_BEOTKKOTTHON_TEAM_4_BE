package com.vacgom.backend.application.disease

import DiseaseFilterRequest
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

    fun filter(dto: DiseaseFilterRequest): List<Disease> {
        val diseases = this.findAll()

        var conditionValue = 0
        dto.condition.forEach {
            conditionValue = conditionValue or it.value
        }

        var ageValue = 0
        dto.age.forEach {
            ageValue = ageValue or it.value
        }

        return diseases.filter { disease ->
            disease.ageFilter and ageValue == ageValue ||
                (
                    disease.conditionalAgeFilter and ageValue == ageValue &&
                        disease.healthConditionFilter and conditionValue > 0 &&
                        disease.forbiddenHealthConditionFilter and conditionValue == 0
                )
        }
    }
}
