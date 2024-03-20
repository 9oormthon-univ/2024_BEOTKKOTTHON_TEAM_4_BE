package com.vacgom.backend.domain.member

import com.vacgom.backend.domain.auth.constants.Role
import com.vacgom.backend.domain.auth.oauth.constants.ProviderType
import com.vacgom.backend.global.auditing.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.util.*

@Entity
@Table(name = "t_member")
class Member(
        var providerId: Long,
        @Enumerated(EnumType.STRING) var providerType: ProviderType,
        @Enumerated(EnumType.STRING) var role: Role,
) : BaseEntity() {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", name = "member_id")
    val id: UUID? = UUID.randomUUID()

    @Embedded
    var memberDetails: MemberDetails? = null

    @Embedded
    var vacgomId: VacgomId? = null

    fun updateVacgomId(vacgomId: VacgomId) {
        this.vacgomId = vacgomId
    }

    fun updateRole(role: Role) {
        this.role = role
    }
}
