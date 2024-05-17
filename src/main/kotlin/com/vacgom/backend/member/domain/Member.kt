package com.vacgom.backend.member.domain

import com.vacgom.backend.auth.domain.constants.ProviderType
import com.vacgom.backend.auth.domain.constants.Role
import com.vacgom.backend.global.auditing.BaseEntity
import com.vacgom.backend.inoculation.domain.Inoculation
import com.vacgom.backend.notification.domain.Notification
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
    var nickname: Nickname? = null

    @OneToMany(mappedBy = "member", cascade = [CascadeType.REMOVE])
    val inoculations: MutableList<Inoculation> = mutableListOf()

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    var healthProfiles: MutableList<HealthProfile> = mutableListOf()

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val notifications: MutableList<Notification> = mutableListOf()

    fun addInoculations(inoculations: List<Inoculation>) {
        this.inoculations.addAll(inoculations)
    }

    fun updateMemberDetails(memberDetails: MemberDetails) {
        this.memberDetails = memberDetails
    }

    fun updateNickname(nickname: Nickname) {
        this.nickname = nickname
    }

    fun updateRole(role: Role) {
        this.role = role
    }
}
