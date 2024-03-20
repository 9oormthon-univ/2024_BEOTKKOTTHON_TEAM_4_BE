package com.vacgom.backend.domain.member

import com.fasterxml.jackson.annotation.JsonFormat
import com.vacgom.backend.domain.auth.constants.Role
import com.vacgom.backend.domain.auth.oauth.constants.ProviderType
import com.vacgom.backend.domain.member.constants.Sex
import com.vacgom.backend.global.auditing.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
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

    @Column(nullable = false)
    var name: String? = null

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd"
    )
    var birthday: LocalDate? = null

    @Enumerated(EnumType.STRING)
    var sex: Sex? = null

    @Embedded
    var vacgomId: VacgomId? = null
}
