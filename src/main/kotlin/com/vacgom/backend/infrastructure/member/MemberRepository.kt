package com.vacgom.backend.infrastructure.member

import com.vacgom.backend.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>
