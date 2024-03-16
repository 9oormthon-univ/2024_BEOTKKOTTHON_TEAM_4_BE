package com.vacgom.backend.presentation.auth

import com.vacgom.backend.application.auth.AuthFactory
import com.vacgom.backend.application.auth.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/v1/oauth")
class AuthController(
        private val authService: AuthService,
        private val authFactory: AuthFactory
) {

    @GetMapping("/{provider}")
    fun redirectToAuthorization(
            @PathVariable provider: String
    ): ResponseEntity<Unit> {
        val authorizationUri: URI = authFactory.getAuthorizationUri(provider)
        val headers = authService.createRedirectHeaders(authorizationUri)
        return ResponseEntity(headers, HttpStatus.FOUND)
    }
}
