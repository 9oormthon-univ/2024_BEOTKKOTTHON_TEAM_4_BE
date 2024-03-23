package com.vacgom.backend.inoculation.application

import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.global.exception.error.GlobalError
import com.vacgom.backend.inoculation.application.dto.request.DiseaseNameRequest
import com.vacgom.backend.inoculation.application.dto.response.InoculationCertificateResponse
import com.vacgom.backend.inoculation.application.dto.response.InoculationDetailResponse
import com.vacgom.backend.inoculation.application.dto.response.InoculationSimpleResponse
import com.vacgom.backend.inoculation.domain.constants.VaccinationType
import com.vacgom.backend.inoculation.infrastructure.persistence.InoculationRepository
import com.vacgom.backend.inoculation.infrastructure.persistence.VaccinationRepository
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class InoculationService(
    private val inoculationRepository: InoculationRepository,
    private val vaccinationRepository: VaccinationRepository,
    private val log: Logger
) {
    fun getInoculationSimpleResponse(
        memberId: UUID,
        vaccinationType: String
    ): List<InoculationSimpleResponse> {
        val validatedVaccinationType = VaccinationType.valueOf(vaccinationType.uppercase())
        val vaccinations = vaccinationRepository.findAllByVaccinationType(validatedVaccinationType)
        val inoculations =
            inoculationRepository.findInoculationsByMemberIdAndVaccinationType(memberId, validatedVaccinationType)

        val hashMap = HashMap<String, MutableList<Long>>()
        inoculations.forEach { inoculation ->
            val vaccineName = inoculation.vaccination.vaccineName
            val inoculationOrder = inoculation.inoculationOrder

            hashMap.computeIfAbsent(vaccineName) { ArrayList() }
            hashMap[vaccineName]!!.add(inoculationOrder)
        }

        return vaccinations.map { vaccination ->
            val vaccineOrders = hashMap[vaccination.vaccineName]?.toHashSet()?.toList() ?: listOf()
            val isCompleted = vaccineOrders.any { order -> order == vaccination.maxOrder }

            InoculationSimpleResponse(
                vaccination.diseaseName,
                vaccination.vaccineName,
                vaccination.minOrder,
                vaccination.maxOrder,
                isCompleted,
                vaccineOrders
            )
        }.toList()
    }

    fun getInoculationDetailResponse(
        memberId: UUID,
        request: DiseaseNameRequest,
        vaccinationType: String
    ): List<InoculationDetailResponse> {
        val validatedVaccinationType = VaccinationType.valueOf(vaccinationType.uppercase())
        val inoculations = (inoculationRepository.findInoculationsByMemberIdAndVaccinationTypeAndDiseaseName(
            memberId,
            validatedVaccinationType,
            request.name
        )
            ?: throw BusinessException(GlobalError.GLOBAL_NOT_FOUND))

        return inoculations.map {
            InoculationDetailResponse(
                it.vaccination.vaccineName,
                it.inoculationOrderString,
                it.agency,
                it.lotNumber,
                it.vaccineName,
                it.vaccineBrandName,
                it.date
            )
        }.toList()
    }

    fun getCertificates(memberId: UUID): List<InoculationCertificateResponse> {
        val inoculations = inoculationRepository.findDistinctLatestInoculationsByMemberId(memberId)
        val sortedByDescending = inoculations.sortedByDescending { it.date }
        return sortedByDescending.map {
            InoculationCertificateResponse(
                it.vaccination.diseaseName,
                it.vaccination.vaccineName,
                it.date,
                it.vaccination.certificationIcon
            )
        }.toList()
    }
}
