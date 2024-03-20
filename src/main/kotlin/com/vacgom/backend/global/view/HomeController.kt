package com.vacgom.backend.global.view

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    @GetMapping("/")
    fun getHomePage(): ResponseEntity<Unit> {
        return ResponseEntity.ok().build()
    }
}
