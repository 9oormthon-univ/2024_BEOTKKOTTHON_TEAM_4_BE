package com.vacgom.backend.inoculation.application.dto.response

import com.vacgom.backend.inoculation.domain.constants.VaccinationType

class VaccineResponse(
    val id: String?,
    val name: String,
    val diseases: List<Long>,
    val diseaseName: String,
    val type: VaccinationType,
)
