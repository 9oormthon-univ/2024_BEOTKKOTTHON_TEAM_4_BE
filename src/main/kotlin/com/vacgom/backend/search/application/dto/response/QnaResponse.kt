package com.vacgom.backend.search.application.dto.response

class QnaResponse(
    val question: String,
    val answer: String,
) {
    companion object {
        fun of(
            question: String,
            answer: String,
        ): QnaResponse {
            return QnaResponse(
                question = question,
                answer = answer,
            )
        }
    }
}
