CREATE TABLE t_disease
(
    disease_id                        BIGINT AUTO_INCREMENT  NOT NULL,
    created_date                      datetime DEFAULT NOW() NOT NULL,
    updated_date                      datetime DEFAULT NOW() NOT NULL,
    name                              VARCHAR(255) NULL,
    icon_image                        VARCHAR(1000) NULL,
    main_image                        VARCHAR(1000) NULL,
    `description`                     VARCHAR(255) NULL,
    age_filter                        INT                    NOT NULL,
    conditional_age_filter            INT                    NOT NULL,
    health_condition_filter           INT                    NOT NULL,
    forbidden_health_condition_filter INT                    NOT NULL,
    CONSTRAINT pk_t_disease PRIMARY KEY (disease_id)
);

CREATE TABLE t_health_profile
(
    health_profile_id BINARY(16)   NOT NULL,
    member_id         BINARY(16)   NULL,
    health_condition  VARCHAR(255) NULL,
    CONSTRAINT pk_t_health_profile PRIMARY KEY (health_profile_id)
);

CREATE TABLE t_inoculation
(
    vaccine_id               BINARY(16)             NOT NULL,
    created_date             datetime DEFAULT NOW() NOT NULL,
    updated_date             datetime DEFAULT NOW() NOT NULL,
    inoculation_order        BIGINT                 NOT NULL,
    inoculation_order_string VARCHAR(255) NULL,
    date                     date NULL,
    agency                   VARCHAR(255) NULL,
    vaccine_name             VARCHAR(255) NULL,
    vaccine_brand_name       VARCHAR(255) NULL,
    lot_number               VARCHAR(255) NULL,
    member_id                BINARY(16)             NULL,
    vaccination_id           BINARY(16)             NULL,
    CONSTRAINT pk_t_inoculation PRIMARY KEY (vaccine_id)
);

CREATE TABLE t_member
(
    member_id     BINARY(16)             NOT NULL,
    created_date  datetime DEFAULT NOW() NOT NULL,
    updated_date  datetime DEFAULT NOW() NOT NULL,
    provider_id   BIGINT                 NOT NULL,
    provider_type VARCHAR(255) NULL,
    `role`        VARCHAR(255) NULL,
    name          VARCHAR(255) NULL,
    birthday      date NULL,
    sex           VARCHAR(255) NULL,
    nickname      VARCHAR(255) NULL,
    CONSTRAINT pk_t_member PRIMARY KEY (member_id)
);

CREATE TABLE t_notification
(
    notification_id BIGINT AUTO_INCREMENT  NOT NULL,
    created_date    datetime DEFAULT NOW() NOT NULL,
    updated_date    datetime DEFAULT NOW() NOT NULL,
    content         VARCHAR(255) NULL,
    type            VARCHAR(255) NULL,
    member_id       BINARY(16)             NULL,
    is_read         BIT(1)                 NOT NULL,
    CONSTRAINT pk_t_notification PRIMARY KEY (notification_id)
);

CREATE TABLE t_qna
(
    qna_id     BIGINT AUTO_INCREMENT NOT NULL,
    question   VARCHAR(255) NULL,
    answer     VARCHAR(255) NULL,
    disease_id BIGINT NULL,
    CONSTRAINT pk_t_qna PRIMARY KEY (qna_id)
);

CREATE TABLE t_vaccination
(
    vaccination_id     BINARY(16)             NOT NULL,
    created_date       datetime DEFAULT NOW() NOT NULL,
    updated_date       datetime DEFAULT NOW() NOT NULL,
    disease_name       VARCHAR(255) NULL,
    vaccine_name       VARCHAR(255) NULL,
    min_order          BIGINT                 NOT NULL,
    max_order          BIGINT                 NOT NULL,
    icon               VARCHAR(1000) NULL,
    certification_icon VARCHAR(1000) NULL,
    vaccination_type   VARCHAR(255) NULL,
    CONSTRAINT pk_t_vaccination PRIMARY KEY (vaccination_id)
);

ALTER TABLE t_member
    ADD CONSTRAINT uc_t_member_nickname UNIQUE (nickname);

ALTER TABLE t_health_profile
    ADD CONSTRAINT FK_T_HEALTH_PROFILE_ON_MEMBER FOREIGN KEY (member_id) REFERENCES t_member (member_id);

ALTER TABLE t_inoculation
    ADD CONSTRAINT FK_T_INOCULATION_ON_MEMBER FOREIGN KEY (member_id) REFERENCES t_member (member_id);

ALTER TABLE t_inoculation
    ADD CONSTRAINT FK_T_INOCULATION_ON_VACCINATION FOREIGN KEY (vaccination_id) REFERENCES t_vaccination (vaccination_id);

ALTER TABLE t_notification
    ADD CONSTRAINT FK_T_NOTIFICATION_ON_MEMBER FOREIGN KEY (member_id) REFERENCES t_member (member_id);

ALTER TABLE t_qna
    ADD CONSTRAINT FK_T_QNA_ON_DISEASE FOREIGN KEY (disease_id) REFERENCES t_disease (disease_id);

INSERT INTO `t_disease` (`disease_id`, `name`, `age_filter`, `conditional_age_filter`, `description`,
                         `forbidden_health_condition_filter`, `health_condition_filter`, `icon_image`, `main_image`,
                         `created_date`, `updated_date`)
VALUES (1, '인플루엔자', 63, 0, '인플루엔자는 인플루엔자 바이러스(Influenza virus)에 의한 감염병으로 매년 겨울철에 유행하여 고열과 함께 기침 등의 호흡기 증상을 일으키는 질환입니다.',
        0, 16383,
        'https://images.vacgom.co.kr/%E1%84%8B%E1%85%B5%E1%86%AB%E1%84%91%E1%85%B3%E1%86%AF%E1%84%85%E1%85%AE%E1%84%8B%E1%85%A6%E1%86%AB%E1%84%8C%E1%85%A1.svg',
        'https://images.vacgom.co.kr/%E1%84%8B%E1%85%B5%E1%86%AB%E1%84%91%E1%85%B3%E1%86%AF%E1%84%85%E1%85%AE%E1%84%8B%E1%85%A6%E1%86%AB%E1%84%8C%E1%85%A1.svg',
        '2024-04-30 14:02:45', '2024-04-30 14:02:45'),
       (2, '폐렴구균', 1, 62,
        '폐렴구균(Streptococcus pneumoniae)은 급성 중이염, 폐렴 및 균혈증, 수막염 등 침습성 감염을 일으키는 주요 원인균 중의 하나이며, 폐렴구균에 의한 침습성 감염은 영아 및 어린 소아와 65세 이상의 고령자에서 발생 빈도가 높습니다.',
        0, 16381,
        'https://images.vacgom.co.kr/%E1%84%91%E1%85%A8%E1%84%85%E1%85%A7%E1%86%B7%E1%84%80%E1%85%AE%E1%84%80%E1%85%B2%E1%86%AB.svg',
        'https://images.vacgom.co.kr/%E1%84%91%E1%85%A8%E1%84%85%E1%85%A7%E1%86%B7%E1%84%80%E1%85%AE%E1%84%80%E1%85%B2%E1%86%AB.svg',
        '2024-04-30 14:02:45', '2024-04-30 14:02:45'),
       (3, '파상풍', 63, 0, '파상풍균(Clostridium tetani)이 생산하는 독소에 의해 유발되는 급성질환으로 파상풍에 이환되면 골격근의 경직과 근육수축이 발생하는 질병입니다.', 0,
        16383,
        'https://images.vacgom.co.kr/%E1%84%83%E1%85%B5%E1%84%91%E1%85%B3%E1%84%90%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8B%E1%85%A1%E3%86%8D%E1%84%91%E1%85%A1%E1%84%89%E1%85%A1%E1%86%BC%E1%84%91%E1%85%AE%E1%86%BC%E3%86%8D%E1%84%87%E1%85%A2%E1%86%A8%E1%84%8B%E1%85%B5%E1%86%AF%E1%84%92%E1%85%A2.svg',
        'https://images.vacgom.co.kr/%E1%84%83%E1%85%B5%E1%84%91%E1%85%B3%E1%84%90%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8B%E1%85%A1%E3%86%8D%E1%84%91%E1%85%A1%E1%84%89%E1%85%A1%E1%86%BC%E1%84%91%E1%85%AE%E1%86%BC%E3%86%8D%E1%84%87%E1%85%A2%E1%86%A8%E1%84%8B%E1%85%B5%E1%86%AF%E1%84%92%E1%85%A2.svg',
        '2024-04-30 14:02:45', '2024-04-30 14:02:45'),
       (4, '디프테리아', 63, 0,
        '디프테리아균(Corynebacterium diphtheriae) 감염 후 발생하는 급성, 독소(toxin) 매개성 호흡기 감염병입니다. 디프테리아는 온대기후 지역에서 상대적으로 발생율이 높으나 전 세계적으로 디프테리아 발생은 매우 드물며, 예방접종으로 국내에서는 1988년 이후부터는 환자가 발생하고 있지 않습니다.',
        0, 16383,
        'https://images.vacgom.co.kr/%E1%84%83%E1%85%B5%E1%84%91%E1%85%B3%E1%84%90%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8B%E1%85%A1%E3%86%8D%E1%84%91%E1%85%A1%E1%84%89%E1%85%A1%E1%86%BC%E1%84%91%E1%85%AE%E1%86%BC%E3%86%8D%E1%84%87%E1%85%A2%E1%86%A8%E1%84%8B%E1%85%B5%E1%86%AF%E1%84%92%E1%85%A2.svg',
        'https://images.vacgom.co.kr/%E1%84%83%E1%85%B5%E1%84%91%E1%85%B3%E1%84%90%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8B%E1%85%A1%E3%86%8D%E1%84%91%E1%85%A1%E1%84%89%E1%85%A1%E1%86%BC%E1%84%91%E1%85%AE%E1%86%BC%E3%86%8D%E1%84%87%E1%85%A2%E1%86%A8%E1%84%8B%E1%85%B5%E1%86%AF%E1%84%92%E1%85%A2.svg',
        '2024-04-30 14:02:45', '2024-04-30 14:02:45'),
       (5, '백일해', 63, 0,
        '백일해는 그람음성간균인(Bordetella pertussis)에 의한 호흡기 감염 질환입니다. 계절에 따른 발병률 차이는 명백히 밝혀진 바 없으나, 여름과 가을에 증가하는 경향을 보이며 전염성이 매우 높아 가족 내 2차 발병률이 80%에 달합니다.',
        0, 16383,
        'https://images.vacgom.co.kr/%E1%84%83%E1%85%B5%E1%84%91%E1%85%B3%E1%84%90%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8B%E1%85%A1%E3%86%8D%E1%84%91%E1%85%A1%E1%84%89%E1%85%A1%E1%86%BC%E1%84%91%E1%85%AE%E1%86%BC%E3%86%8D%E1%84%87%E1%85%A2%E1%86%A8%E1%84%8B%E1%85%B5%E1%86%AF%E1%84%92%E1%85%A2.svg',
        'https://images.vacgom.co.kr/%E1%84%83%E1%85%B5%E1%84%91%E1%85%B3%E1%84%90%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8B%E1%85%A1%E3%86%8D%E1%84%91%E1%85%A1%E1%84%89%E1%85%A1%E1%86%BC%E1%84%91%E1%85%AE%E1%86%BC%E3%86%8D%E1%84%87%E1%85%A2%E1%86%A8%E1%84%8B%E1%85%B5%E1%86%AF%E1%84%92%E1%85%A2.svg',
        '2024-04-30 14:02:45', '2024-04-30 14:02:45'),
       (6, '대상포진', 3, 4,
        '대상포진은 피부분절을 따라서 수포성 발진이 발생하는 질환으로 수두-대상포진바이러스(Varicella-zoster virus, VZV)의 일차 감염 후 감각신경절에 잠복해 있던 바이러스가 재활성화되어 발생하는 질환입니다.',
        490, 15893,
        'https://images.vacgom.co.kr/%E1%84%83%E1%85%A2%E1%84%89%E1%85%A1%E1%86%BC%E1%84%91%E1%85%A9%E1%84%8C%E1%85%B5%E1%86%AB.svg',
        'https://images.vacgom.co.kr/%E1%84%83%E1%85%A2%E1%84%89%E1%85%A1%E1%86%BC%E1%84%91%E1%85%A9%E1%84%8C%E1%85%B5%E1%86%AB.svg',
        '2024-04-30 14:02:45', '2024-04-30 14:02:45'),
       (7, 'A형간염', 63, 0,
        'A형간염은 A형간염 바이러스(Hepatitis A virus, HAV)에 의하여 발생하는 간염으로 환경 및 위생개선과 적절한 예방조치로 예방이 가능합니다. ▶ A형간염은 어떻게 전파되나요? A형간염은 분변-경구 경로로 전파되며, 대부분 사람에서 사람으로 직접적으로 전파되거나 분변에 오염된 물이나 음식물을 섭취함으로써 간접적으로 전파되기도 합니다.',
        0, 16383,
        'https://images.vacgom.co.kr/A%E1%84%92%E1%85%A7%E1%86%BC%E1%84%80%E1%85%A1%E1%86%AB%E1%84%8B%E1%85%A7%E1%86%B7.svg',
        'https://images.vacgom.co.kr/A%E1%84%92%E1%85%A7%E1%86%BC%E1%84%80%E1%85%A1%E1%86%AB%E1%84%8B%E1%85%A7%E1%86%B7.svg',
        '2024-04-30 14:02:45', '2024-04-30 14:02:45'),
       (8, 'B형간염', 63, 0,
        'B형간염 바이러스(Hepatitis B virus, HBV)에 감염되어 간에 염증이 발생하는 질환으로 경과에 따라 급성과 만성으로 구별할 수 있습니다. 급성 B형간염 중 5~10%가 만성 B형간염으로 진행되며 만성 B형간염이 지속되면 간경화증이나 간세포암으로 진행할 수 있습니다. 예방접종으로 국내 B형간염 바이러스 보유자가 많이 감소하였지만 미국 및 유럽의 여러 국가에 비해 아직도 많이 발생하고 있습니다.',
        0, 16383,
        'https://images.vacgom.co.kr/B%E1%84%92%E1%85%A7%E1%86%BC%E1%84%80%E1%85%A1%E1%86%AB%E1%84%8B%E1%85%A7%E1%86%B7.svg',
        'https://images.vacgom.co.kr/B%E1%84%92%E1%85%A7%E1%86%BC%E1%84%80%E1%85%A1%E1%86%AB%E1%84%8B%E1%85%A7%E1%86%B7.svg',
        '2024-04-30 14:02:45', '2024-04-30 14:02:45'),
       (9, '수두', 56, 0,
        '수두는 수두-대상포진 바이러스(Varicella-Zoster virus, VZV)에 의한 일차 감염으로 전염력이 매우 강한 급성 감염질환입니다. 급성의 미열로 시작되고 전신적으로 가렵고 발진성 수포가 발생하는 질환입니다.',
        490, 15893, 'https://images.vacgom.co.kr/%E1%84%89%E1%85%AE%E1%84%83%E1%85%AE.svg',
        'https://images.vacgom.co.kr/%E1%84%89%E1%85%AE%E1%84%83%E1%85%AE.svg', '2024-04-30 14:02:45',
        '2024-04-30 14:02:45'),
       (10, '홍역', 56, 0,
        '홍역은 전 세계적으로 유행하는 급성 발진성 바이러스 질환으로 전염성이 매우 높은 급성 유행성 감염병입니다. 이전에는 소아에서 생명을 위협하는 주요한 질병이었지만 백신이 개발된 이후 그 발생이 현저히 감소하였습니다. 하지만 일부 개발도상국가에서는 아직도 흔히 발생하고 있습니다. 국내에서는 2001년 대유행 이후로는 환자가 급격히 감소하였고, 우리나라는 36개월 이상 토착형 홍역바이러스에 의한 환자발생이 없고, 높은 홍역 예방접종률과 적절한 감시체계 유지,',
        490, 15893,
        'https://images.vacgom.co.kr/%E1%84%92%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%A7%E1%86%A8%E3%86%8D%E1%84%8B%E1%85%B2%E1%84%92%E1%85%A2%E1%86%BC%E1%84%89%E1%85%A5%E1%86%BC%E1%84%8B%E1%85%B5%E1%84%92%E1%85%A1%E1%84%89%E1%85%A5%E1%86%AB%E1%84%8B%E1%85%A7%E1%86%B7%E3%86%8D%E1%84%91%E1%85%AE%E1%86%BC%E1%84%8C%E1%85%B5%E1%86%AB.svg',
        'https://images.vacgom.co.kr/%E1%84%92%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%A7%E1%86%A8%E3%86%8D%E1%84%8B%E1%85%B2%E1%84%92%E1%85%A2%E1%86%BC%E1%84%89%E1%85%A5%E1%86%BC%E1%84%8B%E1%85%B5%E1%84%92%E1%85%A1%E1%84%89%E1%85%A5%E1%86%AB%E1%84%8B%E1%85%A7%E1%86%B7%E3%86%8D%E1%84%91%E1%85%AE%E1%86%BC%E1%84%8C%E1%85%B5%E1%86%AB.svg',
        '2024-04-30 14:02:46', '2024-04-30 14:02:46'),
       (11, '유행성이하선염', 56, 0, '유행성이하선염은 볼거리라고도 하며, 귀 아래의 침샘이 부어오르고 열과 두통이 동반되는 감염성 바이러스 질환입니다.', 490, 15893,
        'https://images.vacgom.co.kr/%E1%84%92%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%A7%E1%86%A8%E3%86%8D%E1%84%8B%E1%85%B2%E1%84%92%E1%85%A2%E1%86%BC%E1%84%89%E1%85%A5%E1%86%BC%E1%84%8B%E1%85%B5%E1%84%92%E1%85%A1%E1%84%89%E1%85%A5%E1%86%AB%E1%84%8B%E1%85%A7%E1%86%B7%E3%86%8D%E1%84%91%E1%85%AE%E1%86%BC%E1%84%8C%E1%85%B5%E1%86%AB.svg',
        'https://images.vacgom.co.kr/%E1%84%92%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%A7%E1%86%A8%E3%86%8D%E1%84%8B%E1%85%B2%E1%84%92%E1%85%A2%E1%86%BC%E1%84%89%E1%85%A5%E1%86%BC%E1%84%8B%E1%85%B5%E1%84%92%E1%85%A1%E1%84%89%E1%85%A5%E1%86%AB%E1%84%8B%E1%85%A7%E1%86%B7%E3%86%8D%E1%84%91%E1%85%AE%E1%86%BC%E1%84%8C%E1%85%B5%E1%86%AB.svg',
        '2024-04-30 14:02:46', '2024-04-30 14:02:46'),
       (12, '풍진', 56, 0, '풍진은 발진, 림프절염을 동반하는 급성 바이러스성 질환입니다. 임신 초기의 임신부가 풍진에 감염될 경우 유산을 하거나 태아에게 선천성 기형을 유발할 수 있습니다.',
        490, 15893,
        'https://images.vacgom.co.kr/%E1%84%92%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%A7%E1%86%A8%E3%86%8D%E1%84%8B%E1%85%B2%E1%84%92%E1%85%A2%E1%86%BC%E1%84%89%E1%85%A5%E1%86%BC%E1%84%8B%E1%85%B5%E1%84%92%E1%85%A1%E1%84%89%E1%85%A5%E1%86%AB%E1%84%8B%E1%85%A7%E1%86%B7%E3%86%8D%E1%84%91%E1%85%AE%E1%86%BC%E1%84%8C%E1%85%B5%E1%86%AB.svg',
        'https://images.vacgom.co.kr/%E1%84%92%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%A7%E1%86%A8%E3%86%8D%E1%84%8B%E1%85%B2%E1%84%92%E1%85%A2%E1%86%BC%E1%84%89%E1%85%A5%E1%86%BC%E1%84%8B%E1%85%B5%E1%84%92%E1%85%A1%E1%84%89%E1%85%A5%E1%86%AB%E1%84%8B%E1%85%A7%E1%86%B7%E3%86%8D%E1%84%91%E1%85%AE%E1%86%BC%E1%84%8C%E1%85%B5%E1%86%AB.svg',
        '2024-04-30 14:02:46', '2024-04-30 14:02:46'),
       (13, '사람유두종바이러스 감염증', 32, 0,
        '사람유두종바이러스는 생식기 감염을 일으키는 가장 흔한 원인 병원체 중 하나로, 고위험군 HPV 감염과 관련 있는 암으로는 자궁경부암, 질암, 외음부암, 음경암, 항문암, 구강암, 구인두암 등이 있고 저위험군 HPV 감염과 관련 있는 질환으로는 생식기 사마귀, 재발성 호흡기 유두종 등이 있습니다.',
        0, 16381,
        'https://images.vacgom.co.kr/%E1%84%89%E1%85%A1%E1%84%85%E1%85%A1%E1%86%B7%E1%84%8B%E1%85%B2%E1%84%83%E1%85%AE%E1%84%8C%E1%85%A9%E1%86%BC%E1%84%87%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%85%E1%85%A5%E1%84%89%E1%85%B3%E1%84%80%E1%85%A1%E1%86%B7%E1%84%8B%E1%85%A7%E1%86%B7%E1%84%8C%E1%85%B3%E1%86%BC.svg',
        'https://images.vacgom.co.kr/%E1%84%89%E1%85%A1%E1%84%85%E1%85%A1%E1%86%B7%E1%84%8B%E1%85%B2%E1%84%83%E1%85%AE%E1%84%8C%E1%85%A9%E1%86%BC%E1%84%87%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%85%E1%85%A5%E1%84%89%E1%85%B3%E1%84%80%E1%85%A1%E1%86%B7%E1%84%8B%E1%85%A7%E1%86%B7%E1%84%8C%E1%85%B3%E1%86%BC.svg',
        '2024-04-30 14:02:46', '2024-04-30 14:02:46');
INSERT INTO `t_vaccination` (`vaccination_id`, `created_date`, `updated_date`, `vaccine_name`, `disease_name`,
                             `max_order`, `min_order`, `vaccination_type`, `certification_icon`, `icon`)
VALUES (X'3E105C94E78511EE9D8F0E9BE882B70B', '2024-03-21 13:16:35', '2024-04-30 16:23:40', 'BCG(피내용)', '결핵', 1, 1,
        'NATION', 'https://images.vacgom.co.kr/%E1%84%91%E1%85%AE%E1%86%B7%E1%84%92%E1%85%A1%E1%84%90%E1%85%B3(5).svg',
        'https://images.vacgom.co.kr/%E1%84%80%E1%85%A7%E1%86%AF%E1%84%92%E1%85%A2%E1%86%A8.svg'),
       (X'3E105EFDE78511EE9D8F0E9BE882B70B', '2024-03-21 13:16:35', '2024-04-30 16:23:40', 'HepB', 'B형간염', 3, 1,
        'NATION', 'https://images.vacgom.co.kr/%E1%84%87%E1%85%A6%E1%84%80%E1%85%A2(1).svg',
        'https://images.vacgom.co.kr/B%E1%84%92%E1%85%A7%E1%86%BC%E1%84%80%E1%85%A1%E1%86%AB%E1%84%8B%E1%85%A7%E1%86%B7.svg'),
       (X'3E106025E78511EE9D8F0E9BE882B70B', '2024-03-21 13:16:35', '2024-04-30 16:23:40', 'DTaP', '디프테리아·파상풍·백일해', 5,
        1, 'NATION', 'https://images.vacgom.co.kr/%E1%84%8B%E1%85%B4%E1%84%89%E1%85%A1(2).svg',
        'https://images.vacgom.co.kr/%E1%84%83%E1%85%B5%E1%84%91%E1%85%B3%E1%84%90%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8B%E1%85%A1%E3%86%8D%E1%84%91%E1%85%A1%E1%84%89%E1%85%A1%E1%86%BC%E1%84%91%E1%85%AE%E1%86%BC%E3%86%8D%E1%84%87%E1%85%A2%E1%86%A8%E1%84%8B%E1%85%B5%E1%86%AF%E1%84%92%E1%85%A2.svg'),
       (X'3E106089E78511EE9D8F0E9BE882B70B', '2024-03-21 13:16:35', '2024-04-30 16:23:40', 'Tdap', '디프테리아·파상풍·백일해', 6,
        6, 'NATION',
        'https://images.vacgom.co.kr/%E1%84%89%E1%85%A9%E1%86%AB%E1%84%92%E1%85%A1%E1%84%90%E1%85%B3(3).svg',
        'https://images.vacgom.co.kr/%E1%84%83%E1%85%B5%E1%84%91%E1%85%B3%E1%84%90%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8B%E1%85%A1%E3%86%8D%E1%84%91%E1%85%A1%E1%84%89%E1%85%A1%E1%86%BC%E1%84%91%E1%85%AE%E1%86%BC%E3%86%8D%E1%84%87%E1%85%A2%E1%86%A8%E1%84%8B%E1%85%B5%E1%86%AF%E1%84%92%E1%85%A2.svg'),
       (X'3E1060E1E78511EE9D8F0E9BE882B70B', '2024-03-21 13:16:35', '2024-04-30 16:23:40', 'IPV', '폴리오', 4, 1, 'NATION',
        'https://images.vacgom.co.kr/%E1%84%87%E1%85%A2%E1%84%8C%E1%85%B5(4).svg',
        'https://images.vacgom.co.kr/%E1%84%83%E1%85%B5%E1%84%91%E1%85%B3%E1%84%90%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8B%E1%85%A1%E3%86%8D%E1%84%91%E1%85%A1%E1%84%89%E1%85%A1%E1%86%BC%E1%84%91%E1%85%AE%E1%86%BC%E3%86%8D%E1%84%87%E1%85%A2%E1%86%A8%E1%84%8B%E1%85%B5%E1%86%AF%E1%84%92%E1%85%A2.svg'),
       (X'3E10612FE78511EE9D8F0E9BE882B70B', '2024-03-21 13:16:35', '2024-04-30 16:23:40', 'Hib', 'b형헤모필루스인플루엔자', 4, 1,
        'NATION', 'https://images.vacgom.co.kr/%E1%84%87%E1%85%A6%E1%84%80%E1%85%A2(1).svg',
        'https://images.vacgom.co.kr/b%E1%84%92%E1%85%A7%E1%86%BC%E1%84%92%E1%85%A6%E1%84%86%E1%85%A9%E1%84%91%E1%85%B5%E1%86%AF%E1%84%85%E1%85%AE%E1%84%89%E1%85%B3%E1%84%8B%E1%85%B5%E1%86%AB%E1%84%91%E1%85%B3%E1%86%AF%E1%84%85%E1%85%AE%E1%84%8B%E1%85%A6%E1%86%AB%E1%84%8C%E1%85%A1.svg'),
       (X'3E10617EE78511EE9D8F0E9BE882B70B', '2024-03-21 13:16:35', '2024-04-30 16:23:40', 'PCV', '폐렴구균', 4, 1,
        'NATION',
        'https://images.vacgom.co.kr/%E1%84%8B%E1%85%B4%E1%84%89%E1%85%A1%E1%84%8C%E1%85%AE%E1%84%89%E1%85%A1%E1%84%80%E1%85%B5(7).svg',
        'https://images.vacgom.co.kr/%E1%84%91%E1%85%A8%E1%84%85%E1%85%A7%E1%86%B7%E1%84%80%E1%85%AE%E1%84%80%E1%85%B2%E1%86%AB.svg'),
       (X'3E1061D3E78511EE9D8F0E9BE882B70B', '2024-03-21 13:16:35', '2024-04-30 16:23:40', '로타바이러스', '로타바이러스 감염증', 3, 1,
        'NATION', 'https://images.vacgom.co.kr/%E1%84%87%E1%85%A6%E1%84%80%E1%85%A2(1).svg',
        'https://images.vacgom.co.kr/%E1%84%85%E1%85%A9%E1%84%90%E1%85%A1%E1%84%87%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%85%E1%85%A5%E1%84%89%E1%85%B3%E1%84%80%E1%85%A1%E1%86%B7%E1%84%8B%E1%85%A7%E1%86%B7%E1%84%8C%E1%85%B3%E1%86%BC.svg'),
       (X'3E106226E78511EE9D8F0E9BE882B70B', '2024-03-21 13:16:35', '2024-04-30 16:23:40', 'MMR', '홍역·유행성이하선염·풍진', 2, 1,
        'NATION', 'https://images.vacgom.co.kr/%E1%84%87%E1%85%A6%E1%84%80%E1%85%A2(1).svg',
        'https://images.vacgom.co.kr/%E1%84%92%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%A7%E1%86%A8%E3%86%8D%E1%84%8B%E1%85%B2%E1%84%92%E1%85%A2%E1%86%BC%E1%84%89%E1%85%A5%E1%86%BC%E1%84%8B%E1%85%B5%E1%84%92%E1%85%A1%E1%84%89%E1%85%A5%E1%86%AB%E1%84%8B%E1%85%A7%E1%86%B7%E3%86%8D%E1%84%91%E1%85%AE%E1%86%BC%E1%84%8C%E1%85%B5%E1%86%AB.svg'),
       (X'3E106283E78511EE9D8F0E9BE882B70B', '2024-03-21 13:16:35', '2024-04-30 16:23:40', 'VAR', '수두', 2, 1, 'NATION',
        'https://images.vacgom.co.kr/%E1%84%8B%E1%85%B4%E1%84%89%E1%85%A1(2).svg',
        'https://images.vacgom.co.kr/%E1%84%89%E1%85%AE%E1%84%83%E1%85%AE.svg'),
       (X'3E1062D7E78511EE9D8F0E9BE882B70B', '2024-03-21 13:16:35', '2024-04-30 16:23:40', 'HepA', 'A형간염', 2, 1,
        'NATION',
        'https://images.vacgom.co.kr/%E1%84%8B%E1%85%B4%E1%84%89%E1%85%A1%E1%84%8C%E1%85%AE%E1%84%89%E1%85%A1%E1%84%80%E1%85%B5(7).svg',
        'https://images.vacgom.co.kr/A%E1%84%92%E1%85%A7%E1%86%BC%E1%84%80%E1%85%A1%E1%86%AB%E1%84%8B%E1%85%A7%E1%86%B7.svg'),
       (X'3E1063ADE78511EE9D8F0E9BE882B70B', '2024-03-21 13:16:35', '2024-04-30 16:23:40', 'IJEV(불활성화백신)', '일본뇌염', 5, 1,
        'NATION',
        'https://images.vacgom.co.kr/%E1%84%8B%E1%85%B4%E1%84%89%E1%85%A1%E1%84%8C%E1%85%AE%E1%84%89%E1%85%A1%E1%84%80%E1%85%B5(7).svg',
        'https://images.vacgom.co.kr/%E1%84%8B%E1%85%B5%E1%86%AF%E1%84%87%E1%85%A9%E1%86%AB%E1%84%82%E1%85%AC%E1%84%8B%E1%85%A7%E1%86%B7.svg'),
       (X'3E10640AE78511EE9D8F0E9BE882B70B', '2024-03-21 13:16:35', '2024-04-30 16:23:40', 'LJEV(약독화생백신)', '일본뇌염', 2, 1,
        'NATION',
        'https://images.vacgom.co.kr/%E1%84%8B%E1%85%B4%E1%84%89%E1%85%A1%E1%84%8C%E1%85%AE%E1%84%89%E1%85%A1%E1%84%80%E1%85%B5(7).svg',
        'https://images.vacgom.co.kr/%E1%84%8B%E1%85%B5%E1%86%AF%E1%84%87%E1%85%A9%E1%86%AB%E1%84%82%E1%85%AC%E1%84%8B%E1%85%A7%E1%86%B7-%E1%84%8B%E1%85%A1%E1%86%A8%E1%84%83%E1%85%A9%E1%86%A8%E1%84%92%E1%85%AA%E1%84%89%E1%85%A2%E1%86%BC.svg'),
       (X'3E106462E78511EE9D8F0E9BE882B70B', '2024-03-21 13:16:35', '2024-04-30 16:23:40', 'HPV', '사람유두종바이러스감염증', 3, 1,
        'NATION', 'https://images.vacgom.co.kr/%E1%84%89%E1%85%A9%E1%86%AB%E1%84%92%E1%85%A1%E1%84%90%E1%85%B3(3).svg',
        'https://images.vacgom.co.kr/%E1%84%89%E1%85%A1%E1%84%85%E1%85%A1%E1%86%B7%E1%84%8B%E1%85%B2%E1%84%83%E1%85%AE%E1%84%8C%E1%85%A9%E1%86%BC%E1%84%87%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%85%E1%85%A5%E1%84%89%E1%85%B3%E1%84%80%E1%85%A1%E1%86%B7%E1%84%8B%E1%85%A7%E1%86%B7%E1%84%8C%E1%85%B3%E1%86%BC.svg'),
       (X'3E1064B9E78511EE9D8F0E9BE882B70B', '2024-03-21 13:16:35', '2024-04-30 16:23:40', 'IIV', '인플루엔자',
        9223372036854775807, 1, 'NATION', 'https://images.vacgom.co.kr/%E1%84%8B%E1%85%B4%E1%84%89%E1%85%A1(2).svg',
        'https://images.vacgom.co.kr/%E1%84%8B%E1%85%B5%E1%86%AB%E1%84%91%E1%85%B3%E1%86%AF%E1%84%85%E1%85%AE%E1%84%8B%E1%85%A6%E1%86%AB%E1%84%8C%E1%85%A1.svg'),
       (X'3E10650EE78511EE9D8F0E9BE882B70B', '2024-03-21 13:16:35', '2024-04-30 16:23:40', 'HPV9(가다실9)', '사람유두종바이러스감염증',
        3, 1, 'EXTRA',
        'https://images.vacgom.co.kr/%E1%84%89%E1%85%A9%E1%86%AB%E1%84%92%E1%85%A1%E1%84%90%E1%85%B3(3).svg',
        'https://images.vacgom.co.kr/%E1%84%89%E1%85%A1%E1%84%85%E1%85%A1%E1%86%B7%E1%84%8B%E1%85%B2%E1%84%83%E1%85%AE%E1%84%8C%E1%85%A9%E1%86%BC%E1%84%87%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%85%E1%85%A5%E1%84%89%E1%85%B3%E1%84%80%E1%85%A1%E1%86%B7%E1%84%8B%E1%85%A7%E1%86%B7%E1%84%8C%E1%85%B3%E1%86%BC.svg'),
       (X'3E106560E78511EE9D8F0E9BE882B70B', '2024-03-21 13:16:35', '2024-04-30 16:23:40', 'COVID19', '코로나19', 3, 1,
        'EXTRA', 'https://images.vacgom.co.kr/%E1%84%87%E1%85%A2%E1%84%8C%E1%85%B5(4).svg',
        'https://images.vacgom.co.kr/%E1%84%8F%E1%85%A9%E1%84%85%E1%85%A9%E1%84%82%E1%85%A119.svg'),
       (X'3E1065ABE78511EE9D8F0E9BE882B70B', '2024-03-21 13:16:35', '2024-04-30 16:23:40', 'MCV(4가)', '수막구균', 1, 1,
        'EXTRA', 'https://images.vacgom.co.kr/%E1%84%87%E1%85%A6%E1%84%80%E1%85%A2(1).svg',
        'https://images.vacgom.co.kr/%E1%84%89%E1%85%AE%E1%84%86%E1%85%A1%E1%86%A8%E1%84%80%E1%85%AE%E1%84%80%E1%85%B2%E1%86%AB.svg');
