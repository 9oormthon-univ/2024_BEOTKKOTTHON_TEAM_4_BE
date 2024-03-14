package com.vacgom.backend.domain.member

import com.vacgom.backend.global.auditing.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "t_member")
class Member(nickname: String) : BaseEntity() {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "nickname")
    val nickname: String

    init {
        this.nickname = nickname
    }
}
