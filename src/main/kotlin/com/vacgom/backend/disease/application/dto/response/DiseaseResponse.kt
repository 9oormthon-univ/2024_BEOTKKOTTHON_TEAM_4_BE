package com.vacgom.backend.disease.application.dto.response

import com.vacgom.backend.disease.domain.Disease
import com.vacgom.backend.search.application.dto.response.QnaResponse

class DiseaseResponse(
    val id: Long?,
    val name: String,
    val mainImage: String?,
    val iconImage: String?,
    val description: String?,
    val qnaList: List<QnaResponse> = listOf(),
) {
    companion object {
        fun of(disease: Disease): DiseaseResponse {
            return DiseaseResponse(
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
