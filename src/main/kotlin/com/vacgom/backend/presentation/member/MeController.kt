package com.vacgom.backend.presentation.member

import com.vacgom.backend.application.auth.dto.response.MeResponse
import com.vacgom.backend.application.member.MemberService
import com.vacgom.backend.domain.disease.HealthCondition
import com.vacgom.backend.exception.member.HealthConditionError
import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.global.security.annotation.AuthId
import com.vacgom.backend.presentation.member.dto.UpdateHealthConditionRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/me")
class MeController(
    private val memberService: MemberService,
) {
    @DeleteMapping
    fun withdraw(
        @AuthId id: UUID,
    ): ResponseEntity<Boolean> {
        this.memberService.withdrawMember(id)

        return ResponseEntity.ok(true)
    }

    @GetMapping
    fun findMe(
        @AuthId id: UUID,
    ): ResponseEntity<MeResponse> {
        val member = this.memberService.findMember(id) ?: throw Error()

        return ResponseEntity.ok(
            MeResponse.of(member),
        )
    }

    @PostMapping("/healthCondition")
    fun updateHealthCondition(
        @AuthId id: UUID,
        @RequestBody request: UpdateHealthConditionRequest,
    ): ResponseEntity<MeResponse> {
        val conditions =
            runCatching {
                request.healthProfiles.map { HealthCondition.valueOf(it) }
            }.getOrElse {
                throw BusinessException(HealthConditionError.INVALID_HEALTH_CONDITION)
            }

        val member =
            this.memberService.updateHealthCondition(
                id,
                conditions,
            )

        return ResponseEntity.ok(
            MeResponse.of(member),
        )
    }
}
