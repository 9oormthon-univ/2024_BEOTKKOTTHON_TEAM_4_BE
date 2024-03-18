package com.vacgom.backend.presentation.auth

import com.vacgom.backend.global.security.annotation.AuthId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/test")
class SimpleController {

    @GetMapping
    fun securityTest(@AuthId id: UUID): ResponseEntity<String> {
        println("UUID/ id = ${id}")
        return ResponseEntity.ok("success!!")
    }
}
