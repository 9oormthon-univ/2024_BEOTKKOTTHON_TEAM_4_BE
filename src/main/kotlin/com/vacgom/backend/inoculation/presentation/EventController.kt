package com.vacgom.backend.inoculation.presentation

import com.vacgom.backend.inoculation.application.InoculationService
import com.vacgom.backend.inoculation.application.dto.request.MemberNameRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/event")
class EventController (
    val inoculationService: InoculationService
){

    @PostMapping
    fun doEvent(@RequestBody memberNameRequest: MemberNameRequest): ResponseEntity<Unit> {
        inoculationService.addEventInoculation(memberNameRequest)
        return ResponseEntity.ok().build()
    }
}
