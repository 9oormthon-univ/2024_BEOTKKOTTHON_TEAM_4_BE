package com.vacgom.backend.presentation.disease

import DiseaseFilterRequest
import com.vacgom.backend.application.disease.DiseaseService
import com.vacgom.backend.application.disease.dto.response.DiseaseResponse
import com.vacgom.backend.domain.disease.AgeCondition
import com.vacgom.backend.domain.disease.HealthCondition
import com.vacgom.backend.presentation.disease.dto.FilterRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/diseases")
class DiseaseController(
    val diseaseService: DiseaseService,
) {
    @GetMapping("")
    fun search(
        @RequestBody body: FilterRequest,
    ): ResponseEntity<List<DiseaseResponse>> {
        val diseases =
            diseaseService.filter(
                DiseaseFilterRequest(
                    age = body.age.map { AgeCondition.valueOf(it) },
                    condition = body.condition.map { HealthCondition.valueOf(it) },
                ),
            )

        return ResponseEntity.ok(diseases.map { DiseaseResponse.of(it) })
    }
}
