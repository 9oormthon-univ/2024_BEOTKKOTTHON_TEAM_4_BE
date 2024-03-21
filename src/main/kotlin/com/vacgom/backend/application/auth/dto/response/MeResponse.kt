package com.vacgom.backend.application.auth.dto.response

import com.vacgom.backend.domain.member.Member

class MeResponse(
    val id: String,
    val nickname: String?,
    val level: String,
    val healthConditions: List<HealthConditionResponse>,
) {
    companion object {
        fun of(member: Member): MeResponse {
            return MeResponse(
                id = member.id.toString(),
                nickname = member.nickname?.nickname,
                level = "레벨",
                healthConditions = member.healthProfiles.map { HealthConditionResponse.of(it.healthCondition) },
            )
        }
    }
}
