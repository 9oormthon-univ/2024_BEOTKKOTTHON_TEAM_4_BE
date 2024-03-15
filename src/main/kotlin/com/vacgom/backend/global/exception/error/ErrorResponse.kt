package com.vacgom.backend.global.exception.error

import org.springframework.validation.FieldError
import java.time.LocalDateTime

data class ErrorResponse(
        val timeStamp: String = LocalDateTime.now().toString(),
        val errorCode: String,
        val errorMessage: String,
        val details: Any? = null
) {
    constructor(
            errorCode: ErrorCode
    ) : this(
            errorCode = errorCode.code,
            errorMessage = errorCode.message
    )

    constructor(
            errorCode: ErrorCode,
            details: Any?
    ) : this(
            errorCode = errorCode.code,
            errorMessage = errorCode.message,
            details = details
    )

    constructor(
            fieldError: FieldError?
    ) : this(
            errorCode = fieldError?.code ?: "",
            errorMessage = fieldError?.defaultMessage ?: ""
    )

    constructor(fieldErrors: List<FieldError>) : this(
            GlobalError.INVALID_REQUEST_PARAM,
            fieldErrors.associate {
                it.field to (it.defaultMessage ?: "null")
            }
    )
}
