package com.vacgom.backend.presentation.search

import com.vacgom.backend.application.search.SearchService
import com.vacgom.backend.application.search.dto.DiseaseSearchResponse
import com.vacgom.backend.application.search.dto.VaccinationSearchResponse
import com.vacgom.backend.domain.disease.AgeCondition
import com.vacgom.backend.domain.disease.HealthCondition
import com.vacgom.backend.presentation.disease.dto.FilterRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/search")
class SearchController(
    val searchService: SearchService,
) {
    @GetMapping("/disease")
    fun disease(
        @RequestBody body: FilterRequest,
    ): ResponseEntity<List<DiseaseSearchResponse>> {
        return ResponseEntity.ok(
            searchService.searchDisease(
                body.age.map { AgeCondition.valueOf(it) },
                body.condition.map { HealthCondition.valueOf(it) },
            ),
        )
    }

    @GetMapping("/vaccination")
    fun vaccination(
        @RequestBody body: FilterRequest,
    ): ResponseEntity<List<VaccinationSearchResponse>> {
        return ResponseEntity.ok(
            searchService.searchVaccination(
                body.age.map { AgeCondition.valueOf(it) },
                body.condition.map { HealthCondition.valueOf(it) },
            ),
        )
    }
}
