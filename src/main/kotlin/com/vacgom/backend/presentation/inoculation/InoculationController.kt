package com.vacgom.backend.presentation.inoculation

import com.vacgom.backend.application.inoculation.dto.InoculationService
import com.vacgom.backend.application.inoculation.dto.request.DiseaseNameRequest
import com.vacgom.backend.application.inoculation.dto.response.InoculationDetailResponse
import com.vacgom.backend.application.inoculation.dto.response.InoculationSimpleResponse
import com.vacgom.backend.global.security.annotation.AuthId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/inoculation")
class InoculationController(
        private val inoculationService: InoculationService
) {
    @GetMapping("/simple")
    fun getInoculationSimpleResponse(
            @AuthId id: UUID,
            @RequestParam type: String
    ): ResponseEntity<List<InoculationSimpleResponse>> {
        val responses = inoculationService.getInoculationSimpleResponse(id, type)
        return ResponseEntity.ok(responses)
    }

    @GetMapping("/detail")
    fun getInoculationDetailResponse(
            @AuthId id: UUID,
            @RequestBody request: DiseaseNameRequest,
            @RequestParam type: String
    ): ResponseEntity<List<InoculationDetailResponse>> {
        val responses = inoculationService.getInoculationDetailResponse(id, request, type)
        return ResponseEntity.ok(responses)
    }
}
