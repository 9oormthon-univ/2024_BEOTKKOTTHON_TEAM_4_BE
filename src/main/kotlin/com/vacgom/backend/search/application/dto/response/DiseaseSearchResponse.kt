package com.vacgom.backend.search.application.dto.response

import com.vacgom.backend.disease.domain.Disease

class DiseaseSearchResponse(
    val id: Long?,
    val name: String,
    val mainImage: String?,
    val iconImage: String?,
    val description: String?,
    val qnaList: List<QnaResponse> = listOf(),
) {
    companion object {
        fun of(disease: Disease): DiseaseSearchResponse {
            return DiseaseSearchResponse(
                id = disease.id,
                name = disease.name,
                mainImage = disease.mainImage,
                iconImage = disease.iconImage,
                description = disease.description,
                qnaList = disease.qnaList.map { QnaResponse.of(it.question, it.answer) },
            )
        }
    }
}
