package com.vacgom.backend.presentation.member

import com.vacgom.backend.application.auth.dto.AuthResponse
import com.vacgom.backend.application.member.MemberService
import com.vacgom.backend.application.member.dto.request.SignUpRequest
import com.vacgom.backend.global.security.annotation.AuthId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/member")
class MemberController(
        private val memberService: MemberService
) {
    @PostMapping("/validation")
    fun validateNickname(@RequestParam nickname: String): ResponseEntity<Unit> {
        memberService.validateNickname(nickname)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/signup")
    fun signUpVacgom(
            @AuthId memberId: UUID,
            @RequestBody request: SignUpRequest
    ): ResponseEntity<AuthResponse> {
        val authResponse = memberService.signUpVacgom(memberId, request)
        return ResponseEntity.ok(authResponse)
    }
}
