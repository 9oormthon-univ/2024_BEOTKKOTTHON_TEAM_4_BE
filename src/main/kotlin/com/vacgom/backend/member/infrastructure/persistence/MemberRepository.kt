package com.vacgom.backend.member.infrastructure.persistence

import com.vacgom.backend.auth.domain.constants.ProviderType
import com.vacgom.backend.auth.domain.constants.Role
import com.vacgom.backend.member.domain.Member
import com.vacgom.backend.member.domain.Nickname
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface MemberRepository : JpaRepository<Member, UUID> {
    fun findByProviderIdAndProviderType(
        providerId: Long,
        providerType: ProviderType,
    ): Member?

    fun existsMemberByNickname(nickname: Nickname): Boolean

    @Query("SELECT COUNT(m)+1 FROM Member m WHERE m.createdDate <= (SELECT m.createdDate FROM Member m WHERE m.id = :uuid)")
    fun getJoinCount(uuid: UUID): Number

    @Query("Select Count(m) From Member m where m.nickname is not null")
    fun countValidUser(): Long
}
