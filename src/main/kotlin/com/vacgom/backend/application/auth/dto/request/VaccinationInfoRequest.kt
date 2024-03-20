package com.vacgom.backend.application.auth.dto.request

data class VaccinationInfoRequest(
        val name: String,
        val birth: String,
        val sex: String,
        val vaccineList: MutableList<Vaccines>
)
