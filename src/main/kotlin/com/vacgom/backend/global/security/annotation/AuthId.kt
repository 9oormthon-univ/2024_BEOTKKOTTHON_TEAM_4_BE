package com.vacgom.backend.global.security.annotation

import org.springframework.security.core.annotation.AuthenticationPrincipal


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : id")
annotation class AuthId
