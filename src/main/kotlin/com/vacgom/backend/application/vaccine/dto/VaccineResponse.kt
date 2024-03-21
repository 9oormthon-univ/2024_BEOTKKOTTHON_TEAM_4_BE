package com.vacgom.backend.application.vaccine.dto

class VaccineResponse(
    val id: String?,
    val name: String,
    val diseases: List<Long>,
)
