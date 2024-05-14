package com.vacgom.backend.inoculation.presentation.dto

import com.vacgom.backend.inoculation.domain.constants.VaccinationType

data class InoculationSimpleRequest(
    val type: VaccinationType,
    val vaccinations: List<String>?,
)
