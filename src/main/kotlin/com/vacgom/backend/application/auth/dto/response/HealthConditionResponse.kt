package com.vacgom.backend.application.auth.dto.response

import com.vacgom.backend.domain.disease.HealthCondition

class HealthConditionResponse(
        val code: String,
        val description: String,
) {
    companion object {
        fun of(healthCondition: HealthCondition): HealthConditionResponse {
            return HealthConditionResponse(
                    code = healthCondition.name,
                    description = healthCondition.description,
            )
        }
    }
}
