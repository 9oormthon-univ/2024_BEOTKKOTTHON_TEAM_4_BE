package com.vacgom.backend.presentation.vaccine

import com.vacgom.backend.application.vaccine.dto.VaccineResponse
import com.vacgom.backend.application.vaccine.dto.VaccineService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/vaccine")
class VaccineController(
    val vaccineService: VaccineService,
) {
    @GetMapping("/{id}")
    fun getVaccine(
        @PathVariable("id") id: String,
    ): ResponseEntity<VaccineResponse> {
        return ResponseEntity.ok(vaccineService.getVaccine(id))
    }
}
