package com.vacgom.backend.application.member.dto.request

import com.vacgom.backend.domain.member.constants.HealthCondition

data class SignUpRequest(
        val name: String,
        val birthday: String,
        val sex: String,
        val healthConditions: MutableList<HealthCondition>,
        val nickname: String
)
