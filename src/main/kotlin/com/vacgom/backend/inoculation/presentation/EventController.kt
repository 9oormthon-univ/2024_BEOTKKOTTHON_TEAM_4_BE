package com.vacgom.backend.inoculation.presentation

import com.vacgom.backend.inoculation.application.InoculationService
import com.vacgom.backend.inoculation.presentation.dto.EventVaccinationRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/event")
class EventController(
    val inoculationService: InoculationService,
) {
    @PostMapping
    fun issueEventVaccination(
        @RequestBody request: EventVaccinationRequest,
    ): ResponseEntity<Unit> {
        inoculationService.addEventInoculation(request)
        return ResponseEntity.ok().build()
    }
}
