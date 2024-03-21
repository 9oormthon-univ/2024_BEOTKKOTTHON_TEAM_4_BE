package com.vacgom.backend.application.vaccine.dto

import com.vacgom.backend.application.disease.DiseaseService
import com.vacgom.backend.exception.inoculation.VaccineError
import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.infrastructure.inoculation.persistence.VaccinationRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class VaccineService(
    val vaccineRepository: VaccinationRepository,
    val diseaseService: DiseaseService,
) {
    fun getVaccine(id: String): VaccineResponse {
        val vaccine =
            vaccineRepository.findById(UUID.fromString(id))
                .orElseThrow { throw BusinessException(VaccineError.UNKNOWN_VACCINE_REQUESTED) }

        val diseases = diseaseService.findAll()

        return VaccineResponse(
            id = vaccine.id?.toString(),
            name = vaccine.vaccineName,
            diseases =
                diseases.filter { vaccine.diseaseName.contains(it.name) }
                    .mapNotNull { it.id }
                    .toList(),
        )
    }
}
