package com.vacgom.backend.presentation.member

import com.vacgom.backend.application.member.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/member")
class MemberController(
        private val memberService: MemberService
) {
    @PostMapping("/validation")
    fun validateVacgomId(@RequestParam vacgomId: String): ResponseEntity<Unit> {
        memberService.validateVacgomId(vacgomId)
        return ResponseEntity.ok().build()
    }
}
