package com.vacgom.backend.application.disease.dto.response

import com.vacgom.backend.domain.disease.Disease

class DiseaseResponse(
    val id: Long?,
    val name: String,
    val iconImage: String?,
    val description: String?,
) {
    companion object {
        fun of(disease: Disease): DiseaseResponse {
            return DiseaseResponse(
                id = disease.id,
                name = disease.name,
                iconImage = disease.iconImage,
                description = disease.description,
            )
        }
    }
}
