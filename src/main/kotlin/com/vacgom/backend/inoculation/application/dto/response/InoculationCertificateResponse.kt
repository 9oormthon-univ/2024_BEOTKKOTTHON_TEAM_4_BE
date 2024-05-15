package com.vacgom.backend.inoculation.application.dto.response

import com.vacgom.backend.inoculation.domain.constants.VaccinationType
import java.time.LocalDate

data class InoculationCertificateResponse(
    val userId: String,
    val vaccineId: String,
    val diseaseName: String,
    val vaccineName: String,
    val inoculatedDate: LocalDate,
    val iconImage: String,
    val type: VaccinationType,
)
