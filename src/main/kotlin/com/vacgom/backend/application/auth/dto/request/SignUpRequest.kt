package com.vacgom.backend.application.auth.dto.request

data class SignUpRequest(
        val memberInfo: MemberInfoRequest,
        val vaccinationInfo: VaccinationInfoRequest
)
