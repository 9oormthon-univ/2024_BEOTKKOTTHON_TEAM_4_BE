package com.vacgom.backend.infrastructure.member.persistence

import com.vacgom.backend.domain.auth.oauth.constants.ProviderType
import com.vacgom.backend.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, UUID> {
    fun findByProviderIdAndProviderType(
            providerId: Long,
            providerType: ProviderType
    ): Member?
}
