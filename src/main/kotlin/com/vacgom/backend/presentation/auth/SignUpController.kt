package com.vacgom.backend.presentation.auth

import com.vacgom.backend.application.auth.VacgomSignupService
import com.vacgom.backend.application.auth.dto.request.SignUpRequest
import com.vacgom.backend.application.auth.dto.response.AuthResponse
import com.vacgom.backend.global.security.annotation.AuthId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api/v1/member")
class SignUpController(
        private val vacgomSignupService: VacgomSignupService
) {
    @PostMapping("/validation")
    fun validateNickname(@RequestParam nickname: String): ResponseEntity<Unit> {
        vacgomSignupService.validateNickname(nickname)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/signup")
    fun signUpVacgom(
            @AuthId memberId: UUID,
            @RequestBody request: SignUpRequest
    ): ResponseEntity<AuthResponse> {
        val authResponse = vacgomSignupService.signUpVacgom(memberId, request)
        return ResponseEntity.ok(authResponse)
    }
}
