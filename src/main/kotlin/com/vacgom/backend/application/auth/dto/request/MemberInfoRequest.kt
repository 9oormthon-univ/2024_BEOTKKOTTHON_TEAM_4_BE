package com.vacgom.backend.application.auth.dto.request

import com.vacgom.backend.domain.disease.HealthCondition

data class MemberInfoRequest(
    val nickname: String,
    val healthConditions: MutableList<HealthCondition>,
)
