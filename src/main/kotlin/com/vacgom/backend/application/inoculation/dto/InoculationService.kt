package com.vacgom.backend.application.inoculation.dto

import com.vacgom.backend.application.inoculation.dto.response.InoculationSimpleResponse
import com.vacgom.backend.domain.inoculation.constants.VaccinationType
import com.vacgom.backend.infrastructure.inoculation.persistence.InoculationRepository
import com.vacgom.backend.infrastructure.inoculation.persistence.VaccinationRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class InoculationService(
        private val inoculationRepository: InoculationRepository,
        private val vaccinationRepository: VaccinationRepository
) {
    fun getInoculationSimpleResponse(
            memberId: UUID,
            vaccinationType: String
    ): List<InoculationSimpleResponse> {
        val validatedVaccinationType = VaccinationType.valueOf(vaccinationType.uppercase())
        val vaccinations = vaccinationRepository.findAllByVaccinationType(validatedVaccinationType)
        val inoculations = inoculationRepository.findInoculationsByMemberIdAndVaccinationType(memberId, validatedVaccinationType)

        val hashMap = HashMap<String, MutableList<Long>>()
        inoculations.forEach { inoculation ->
            val vaccineName = inoculation.vaccination.vaccineName
            val inoculationOrder = inoculation.inoculationOrder

            hashMap.computeIfAbsent(vaccineName) { ArrayList() }
            hashMap[vaccineName]!!.add(inoculationOrder)
        }

        return vaccinations.map { vaccination ->
            val vaccineOrders = hashMap[vaccination.vaccineName]?.toHashSet()?.toList()
            InoculationSimpleResponse(vaccination.diseaseName, vaccination.vaccineName, vaccination.minOrder, vaccination.maxOrder, vaccineOrders)
        }.toList()
    }
}
