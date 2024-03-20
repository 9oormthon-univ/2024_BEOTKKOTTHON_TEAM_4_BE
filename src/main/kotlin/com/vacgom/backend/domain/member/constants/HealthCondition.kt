package com.vacgom.backend.domain.member.constants

enum class HealthCondition(
        val code: Long,
        val description: String
) {
    DIABETES(1, "당뇨병"),
    CHRONIC_CARDIOVASCULAR_DISEASE(2, "만성 심혈관질환"),
    CHRONIC_PULMONARY_DISEASE(3, "만성 폐질환"),
    CHRONIC_RENAL_DISEASE(4, "만성 신질환"),
    CHRONIC_LIVER_DISEASE(5, "만성 간질환"),
    SOLID_TUMOR_UNDERGOING_ANTINEOPLASTIC_THERAPY(6, "항암치료중인 고형암"),
    IMMUNOSUPPRESSIVE_AGENTS_EXCLUDING_TRANSPLANT(7, "이식 이외 면역 억제제 사용"),
    HEMATOPOIETIC_STEM_CELL_TRANSPLANTATION(8, "조혈모"),
    CELL_TRANSPLANTATION(9, "세포이식"),
    SICKLE_CELL_DISEASE(10, "무비증"),
    HIV_INFECTION_CD4_ABOVE_200(11, "HIV 감염:CD4>=200/mm3"),
    HIV_INFECTION_CD4_BELOW_200(11, "HIV 감염:CD4<200/mm3"),
    PREGNANCY(12, "임신"),
    MEDICAL_WORKER(13, "의료기관 종사자"),
    ORGAN_TRANSPLANTATION(14, "장기 이식 경험")
}
