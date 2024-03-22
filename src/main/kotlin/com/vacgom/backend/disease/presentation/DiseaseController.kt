package com.vacgom.backend.disease.presentation

import com.vacgom.backend.disease.application.DiseaseService
import com.vacgom.backend.disease.application.dto.response.DiseaseResponse
import com.vacgom.backend.disease.domain.constants.HealthCondition
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/diseases")
class DiseaseController(
    val diseaseService: DiseaseService,
) {
    @GetMapping("/healthConditions")
    fun healthConditions(): ResponseEntity<List<Pair<String, String>>> {
        return ResponseEntity.ok(HealthCondition.entries.map { Pair(it.name, it.description) })
    }

    @GetMapping("/{id}")
    fun getDisease(
        @PathVariable id: Long,
    ): ResponseEntity<DiseaseResponse> {
        val disease = diseaseService.findById(id)

        return ResponseEntity.ok(DiseaseResponse.of(disease))
    }
}
