package com.vacgom.backend.presentation.member

import com.vacgom.backend.application.auth.dto.response.MeResponse
import com.vacgom.backend.application.member.MemberService
import com.vacgom.backend.global.security.annotation.AuthId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/me")
class MeController(
    private val memberService: MemberService,
) {
    @GetMapping
    fun findMe(
        @AuthId id: UUID,
    ): ResponseEntity<MeResponse> {
        val member = this.memberService.findMember(id) ?: throw Error()

        return ResponseEntity.ok(
            MeResponse.of(member),
        )
    }
}
