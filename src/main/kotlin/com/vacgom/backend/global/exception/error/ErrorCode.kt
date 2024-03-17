package com.vacgom.backend.global.exception.error

import org.springframework.http.HttpStatus

interface ErrorCode {
    val message: String
    val status: HttpStatus
    val code: String
}
