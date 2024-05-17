package com.vacgom.backend.inoculation.infrastructure.persistence

import com.vacgom.backend.inoculation.domain.Inoculation
import com.vacgom.backend.inoculation.domain.constants.VaccinationType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface InoculationRepository : JpaRepository<Inoculation, UUID> {
    @Query(
        "select i " +
            "from Inoculation i " +
            "where i.member.id = :memberId " +
            "order by i.date desc",
    )
    fun findInoculationsByMemberId(memberId: UUID): List<Inoculation>

    @Query(
        "select i " +
            "from Inoculation i " +
            "where i.member.id = :memberId " +
            "and i.vaccination.vaccinationType = :vaccinationType",
    )
    fun findInoculationsByMemberIdAndVaccinationType(
        memberId: UUID,
        vaccinationType: VaccinationType,
    ): List<Inoculation>

    @Query(
        "select distinct i.vaccination.diseaseName " +
            "from Inoculation i " +
            "where i.member.id = :memberId",
    )
    fun findDistinctDiseaseNameByMemberId(memberId: UUID): List<String>

    @Query(
        "select i " +
            "from Inoculation i " +
            "where i.member.id = :memberId and i.vaccination.id = :vaccineId " +
            "and i.vaccination.vaccinationType = :vaccinationType " +
            "order by i.date desc",
    )
    fun findInoculationsByMemberIdAndVaccinationTypeAndVaccineId(
        memberId: UUID,
        vaccinationType: VaccinationType,
        vaccineId: UUID,
    ): List<Inoculation>?

    @Query(
        "select i " +
            "from Inoculation i " +
            "where i.member.id = :memberId and i.vaccination.diseaseName LIKE %:diseaseName% " +
            "and i.vaccination.vaccinationType = :vaccinationType " +
            "order by i.date desc",
    )
    fun findInoculationsByMemberIdAndVaccinationTypeAndDiseaseName(
        memberId: UUID,
        vaccinationType: VaccinationType,
        diseaseName: String,
    ): List<Inoculation>?

    @Query(
        "SELECT i " +
            "FROM Inoculation i JOIN i.vaccination v " +
            "WHERE i.member.id = :memberId " +
            "ORDER BY i.inoculationOrder DESC",
    )
    fun findDistinctLatestInoculationsByMemberId(memberId: UUID): List<Inoculation>

    @Query(
        "SELECT i " +
            "from Inoculation i " +
            "where i.vaccination.diseaseName = :diseaseName",
    )
    fun findInoculationsByDiseaseName(diseaseName: String): List<Inoculation>

    @Query(
        "SELECT i from Inoculation i " +
            "where i.vaccination.id = :vaccinationId " +
            "and i.member.id = :memberId " +
            "ORDER BY i.date DESC, i.inoculationOrder DESC LIMIT 1",
    )
    fun findLastUserInoculation(
        vaccinationId: UUID,
        memberId: UUID,
    ): Inoculation?
}
