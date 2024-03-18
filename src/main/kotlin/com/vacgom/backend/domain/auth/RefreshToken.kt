package com.vacgom.backend.domain.auth

import com.vacgom.backend.domain.member.Member
import com.vacgom.backend.global.auditing.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "t_refresh_token")
class RefreshToken(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id", nullable = false)
        var member: Member,
        var token: String,
        var userAgent: String,
        var expiredAt: LocalDateTime
) : BaseEntity() {
    @Id
    @GeneratedValue
    @Column(name = "rt_id")
    private val id: Long? = null
}

