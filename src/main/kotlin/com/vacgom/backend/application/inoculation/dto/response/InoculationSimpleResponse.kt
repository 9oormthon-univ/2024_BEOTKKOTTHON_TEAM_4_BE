package com.vacgom.backend.application.inoculation.dto.response

data class InoculationSimpleResponse(
        val diseaseName: String,
        val vaccineName: String,
        val minOrder: Long,
        val maxOrder: Long,
        val isCompleted: Boolean,
        val inoculationOrders: List<Long>?
)
