package com.vacgom.backend.inoculation.presentation

import com.vacgom.backend.global.security.annotation.AuthId
import com.vacgom.backend.inoculation.application.InoculationService
import com.vacgom.backend.inoculation.application.dto.request.DiseaseNameRequest
import com.vacgom.backend.inoculation.application.dto.response.InoculationCertificateResponse
import com.vacgom.backend.inoculation.application.dto.response.InoculationDetailResponse
import com.vacgom.backend.inoculation.application.dto.response.InoculationSimpleResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/inoculation")
class InoculationController(
    private val inoculationService: InoculationService,
) {
    @GetMapping("/simple")
    fun getInoculationSimpleResponse(
        @AuthId id: UUID,
        @RequestParam type: String,
    ): ResponseEntity<List<InoculationSimpleResponse>> {
        val responses = inoculationService.getInoculationSimpleResponse(id, type)
        return ResponseEntity.ok(responses)
    }

    @PostMapping("/detail")
    fun getInoculationDetailResponse(
        @AuthId id: UUID,
        @RequestBody request: DiseaseNameRequest,
        @RequestParam type: String,
    ): ResponseEntity<List<InoculationDetailResponse>> {
        val responses = inoculationService.getInoculationDetailResponse(id, request, type)
        return ResponseEntity.ok(responses)
    }

    @GetMapping("/certificate")
    fun getInoculationCertificateResponse(
        @AuthId id: UUID,
    ): ResponseEntity<List<InoculationCertificateResponse>> {
        val certificates = inoculationService.getCertificates(id)
        return ResponseEntity.ok(certificates)
    }
}
