package com.vacgom.backend.disease.domain

import jakarta.persistence.*

@Entity()
@Table(name = "t_qna")
class Qna(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id")
    val id: Long? = null,
    val question: String,
    val answer: String,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "disease_id")
    val disease: Disease,
)
