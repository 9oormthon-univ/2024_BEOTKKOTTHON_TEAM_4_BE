package com.vacgom.backend.infrastructure.member

import com.vacgom.backend.domain.member.Member
import com.vacgom.backend.domain.member.constants.ProviderType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, UUID> {
    fun findByProviderIdAndProviderType(
            providerId: Long,
            providerType: ProviderType
    ): Member?
}
