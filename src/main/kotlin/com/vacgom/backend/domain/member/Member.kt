package com.vacgom.backend.domain.member

import com.vacgom.backend.global.auditing.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "t_user")
class User(
        val nickname: String
) : BaseEntity() {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
