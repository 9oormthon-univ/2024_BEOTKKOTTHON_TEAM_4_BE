package com.vacgom.backend.inoculation.application

import com.vacgom.backend.disease.application.DiseaseService
import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.global.exception.error.GlobalError
import com.vacgom.backend.inoculation.application.dto.request.DiseaseNameRequest
import com.vacgom.backend.inoculation.application.dto.response.InoculationCertificateResponse
import com.vacgom.backend.inoculation.application.dto.response.InoculationDetailResponse
import com.vacgom.backend.inoculation.application.dto.response.InoculationSimpleResponse
import com.vacgom.backend.inoculation.domain.Inoculation
import com.vacgom.backend.inoculation.domain.constants.VaccinationType
import com.vacgom.backend.inoculation.infrastructure.persistence.InoculationRepository
import com.vacgom.backend.inoculation.infrastructure.persistence.VaccinationRepository
import com.vacgom.backend.inoculation.presentation.dto.EventVaccinationRequest
import com.vacgom.backend.inoculation.presentation.dto.InoculationSimpleRequest
import com.vacgom.backend.member.domain.Nickname
import com.vacgom.backend.member.infrastructure.persistence.MemberRepository
import com.vacgom.backend.notification.application.NotificationService
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.LocalDate
import java.util.*

@Service
@Transactional
class InoculationService(
    private val inoculationRepository: InoculationRepository,
    private val vaccinationRepository: VaccinationRepository,
    private val memberRepository: MemberRepository,
    private val notificationService: NotificationService,
    private val log: Logger,
    @Value("\${image-gen.url}") private val imageGenUrl: String,
    private val diseaseService: DiseaseService,
) {
    fun getInoculationSimpleResponse(
        memberId: UUID,
        request: InoculationSimpleRequest,
    ): List<InoculationSimpleResponse> {
        val vaccinations =
            if (request.vaccinations.isNullOrEmpty()) {
                vaccinationRepository.findAll().filter {
                    it.vaccinationType == request.type
                }
            } else {
                vaccinationRepository.findAll().filter {
                    it.vaccineName in request.vaccinations && it.vaccinationType == request.type
                }
            }

        val inoculations =
            inoculationRepository.findInoculationsByMemberId(memberId)

        val hashMap = HashMap<String, MutableList<Long>>()
        inoculations.forEach { inoculation ->
            val vaccineName = inoculation.vaccination.vaccineName
            val inoculationOrder = inoculation.inoculationOrder

            hashMap.computeIfAbsent(vaccineName) { ArrayList() }
            hashMap[vaccineName]!!.add(inoculationOrder)
        }

        val diseases = diseaseService.findAll()

        return vaccinations.map { vaccination ->
            val vaccineOrders = hashMap[vaccination.vaccineName]?.toHashSet()?.toList() ?: listOf()
            val isCompleted = vaccineOrders.any { order -> order == vaccination.maxOrder }

            val disease = diseases.find { vaccination.diseaseName.contains(it.name) }

            InoculationSimpleResponse(
                vaccination.id.toString(),
                disease?.id,
                vaccination.diseaseName,
                vaccination.vaccineName,
                vaccination.minOrder,
                vaccination.maxOrder,
                isCompleted,
                vaccineOrders,
            )
        }.toList()
    }

    fun getInoculationDetailResponse(
        memberId: UUID,
        request: DiseaseNameRequest,
        vaccinationType: String,
    ): List<InoculationDetailResponse> {
        val validatedVaccinationType = VaccinationType.valueOf(vaccinationType.uppercase())
        val inoculations = (
            inoculationRepository.findInoculationsByMemberIdAndVaccinationTypeAndVaccineId(
                memberId,
                validatedVaccinationType,
                UUID.fromString(request.vaccineId),
            )
                ?: throw BusinessException(GlobalError.GLOBAL_NOT_FOUND)
        )

        return inoculations.map {
            println(it.vaccineName.isNullOrEmpty())

            println(it.vaccineName ?: "vaccineName")
            InoculationDetailResponse(
                it.vaccination.id.toString(),
                it.vaccination.vaccineName,
                it.inoculationOrderString,
                it.agency,
                if (it.lotNumber.isNullOrEmpty()) "로트번호 정보 없음" else it.lotNumber,
                if (it.vaccineName.isNullOrEmpty()) "백신명 정보 없음" else it.vaccineName,
                if (it.vaccineBrandName.isNullOrEmpty()) "백신 제조사 정보 없음" else it.vaccineBrandName,
                it.date,
            )
        }.toList()
    }

    fun getCertificates(
        memberId: UUID,
        orderBy: String,
    ): List<InoculationCertificateResponse> {
        val inoculations1 = inoculationRepository.findInoculationsByMemberId(memberId)

        val group =
            inoculations1.groupBy {
                it.vaccination.vaccineName
            }.map {
                it.value.last()
            }

        val result =
            group.map {
                InoculationCertificateResponse(
                    memberId.toString(),
                    it.vaccination.id.toString(),
                    it.vaccination.diseaseName,
                    it.vaccination.vaccineName,
                    it.date,
                    it.vaccination.certificationIcon,
                    it.vaccination.vaccinationType,
                )
            }.toList()

        return when (orderBy) {
            "dateDesc" -> result.sortedByDescending { it.inoculatedDate }
            "dateAsc" -> result.sortedBy { it.inoculatedDate }
            else -> result
        }
    }

    fun getCertificateImage(
        userId: UUID,
        inoculationId: String,
    ): ByteArray {
        val inoculation =
            inoculationRepository.findLastUserInoculation(UUID.fromString(inoculationId), userId)
                ?: throw BusinessException(GlobalError.GLOBAL_NOT_FOUND)

        if (inoculation.member.id != userId) {
            throw BusinessException(GlobalError.GLOBAL_NOT_FOUND)
        }

        val restTemplate = RestTemplate()
        return restTemplate.getForObject<ByteArray>(
            imageGenUrl + "?diseaseName=${inoculation.vaccination.diseaseName}" +
                "&vaccinationName=${inoculation.vaccination.vaccineName}" +
                "&inoculationDate=${inoculation.date}" +
                "&userId=${inoculation.member.nickname?.nickname}" +
                "&cardImageUrl=${inoculation.vaccination.certificationBackgroundImage}" + "" +
                "&maskImageUrl=${inoculation.vaccination.certificationMaskImage}" +
                "&iconImageUrl=${inoculation.vaccination.certificationIcon}",
            ByteArray::class.java,
        ) ?: throw BusinessException(GlobalError.GLOBAL_NOT_FOUND)
    }

    fun addEventInoculation(eventVaccinationRequest: EventVaccinationRequest) {
        val member =
            memberRepository.findMemberByNickname(
                Nickname(eventVaccinationRequest.userId),
            ) ?: throw BusinessException(GlobalError.GLOBAL_NOT_FOUND)
        val eventVaccination =
            vaccinationRepository.findById(UUID.fromString("3e1065ab-e785-11ee-9d8f-0e9be882b70f"))
                .orElseThrow { BusinessException(GlobalError.GLOBAL_NOT_FOUND) }

        inoculationRepository.save(
            Inoculation(
                1,
                "이벤트",
                LocalDate.now(),
                "구름스퀘어",
                "GUAP 이벤트 백신",
                "백곰",
                "https://vacgom.co.kr",
                member,
                eventVaccination,
            ),
        )
        notificationService.sendNotification(member.id!!, "이벤트 백신 접종 증명서가 발급되었어요!", "event")
    }
}
