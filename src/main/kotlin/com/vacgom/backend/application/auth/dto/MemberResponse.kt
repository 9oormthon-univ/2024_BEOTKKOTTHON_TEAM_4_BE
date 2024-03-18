package com.vacgom.backend.application.auth.dto

import com.vacgom.backend.domain.auth.constants.Role
import java.util.*

data class MemberResponse(
        val memberId: UUID,
        val role: Role
)
