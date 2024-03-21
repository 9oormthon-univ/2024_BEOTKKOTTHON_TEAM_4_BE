package com.vacgom.backend.application.inoculation.dto

import com.vacgom.backend.infrastructure.inoculation.persistence.InoculationRepository
import java.util.*

class InoculationService(
        private val inoculationRepository: InoculationRepository
) {
    fun getInoculationSimpleResponse(
            id: UUID,
            vaccinationType: String
    ) {
        
    }
}
