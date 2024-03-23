package com.vacgom.backend.search.presentation

import com.vacgom.backend.disease.application.dto.request.FilterRequest
import com.vacgom.backend.disease.domain.constants.AgeCondition
import com.vacgom.backend.disease.domain.constants.HealthCondition
import com.vacgom.backend.global.security.annotation.AuthId
import com.vacgom.backend.inoculation.domain.constants.VaccinationType
import com.vacgom.backend.search.application.SearchService
import com.vacgom.backend.search.application.dto.VaccinationSearchResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/search")
class SearchController(
        val searchService: SearchService,
) {
    @GetMapping
    fun disease(
            @RequestBody body: FilterRequest,
            @RequestParam type: String,
    ): ResponseEntity<List<Any>> {
        when (type) {
            "essential" -> {
                return ResponseEntity.ok(
                        searchService.searchDisease(
                                body.age.map { AgeCondition.valueOf(it) },
                                body.condition.map { HealthCondition.valueOf(it) },
                        ),
                )
            }

            "nation" -> {
                return ResponseEntity.ok(
                        searchService.searchVaccination(
                                body.age.map { AgeCondition.valueOf(it) },
                                body.condition.map { HealthCondition.valueOf(it) },
                                VaccinationType.NATION,
                        ),
                )
            }

            "extra" -> {
                return ResponseEntity.ok(
                        searchService.searchVaccination(
                                body.age.map { AgeCondition.valueOf(it) },
                                body.condition.map { HealthCondition.valueOf(it) },
                                VaccinationType.EXTRA,
                        ),
                )
            }

            else -> {
                throw IllegalArgumentException("Invalid type")
            }
        }
    }

    @GetMapping("/recommend")
    fun recommendVaccination(@AuthId id: UUID): ResponseEntity<List<VaccinationSearchResponse>> {
        return ResponseEntity.ok(searchService.searchRecommendVaccination(id))
    }
}
