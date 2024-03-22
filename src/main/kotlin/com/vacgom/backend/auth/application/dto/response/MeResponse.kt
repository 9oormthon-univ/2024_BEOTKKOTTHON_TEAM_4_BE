package com.vacgom.backend.auth.application.dto.response

import com.vacgom.backend.member.domain.Member

class MeResponse(
    val id: String,
    val nickname: String?,
    val healthConditions: List<HealthConditionResponse>,
) {
    companion object {
        fun of(member: Member): MeResponse {
            return MeResponse(
                id = member.id.toString(),
                nickname = member.nickname?.nickname,
                healthConditions = member.healthProfiles.map { HealthConditionResponse.of(it.healthCondition) },
            )
        }
    }
}
