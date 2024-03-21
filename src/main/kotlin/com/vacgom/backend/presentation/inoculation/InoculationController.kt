package com.vacgom.backend.presentation.inoculation

import com.vacgom.backend.application.inoculation.dto.InoculationService
import com.vacgom.backend.application.inoculation.dto.response.InoculationSimpleResponse
import com.vacgom.backend.global.security.annotation.AuthId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/inoculation")
class InoculationController(
        private val inoculationService: InoculationService
) {
    @GetMapping
    fun getInoculationSimpleResponse(
            @AuthId id: UUID,
            @RequestParam type: String
    ): ResponseEntity<List<InoculationSimpleResponse>> {
        val responses = inoculationService.getInoculationSimpleResponse(id, type)
        return ResponseEntity.ok(responses)
    }
}
