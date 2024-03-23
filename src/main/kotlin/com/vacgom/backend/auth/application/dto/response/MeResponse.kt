package com.vacgom.backend.auth.application.dto.response

import com.vacgom.backend.member.domain.Member

class MeResponse(
    val id: String,
    val nickname: String?,
    val name: String,
    val healthConditions: List<HealthConditionResponse>,
) {
    companion object {
        fun of(member: Member): MeResponse {
            return MeResponse(
                id = member.id.toString(),
                nickname = member.nickname?.nickname,
                name = member.memberDetails!!.name,
                healthConditions = member.healthProfiles.map { HealthConditionResponse.of(it.healthCondition) },
            )
        }
    }
}
