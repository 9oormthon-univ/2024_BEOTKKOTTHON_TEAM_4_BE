package com.vacgom.backend.global.exception

import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.global.exception.error.ErrorCode
import com.vacgom.backend.global.exception.error.ErrorResponse
import com.vacgom.backend.global.exception.error.GlobalError
import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler(private val log: Logger) {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ErrorResponse? {
        return ErrorResponse(ex.fieldErrors)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun missingServletRequestParameterException(): ErrorResponse? {
        return ErrorResponse(GlobalError.INVALID_REQUEST_PARAM)
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(
            exception: BusinessException
    ): ResponseEntity<ErrorResponse?> {
        logBusinessException(exception)
        return convert(exception.errorCode)
    }

    private fun convert(
            errorCode: ErrorCode
    ): ResponseEntity<ErrorResponse?> {
        return ResponseEntity
                .status(errorCode.status)
                .body(ErrorResponse(errorCode))
    }

    private fun logBusinessException(exception: BusinessException) {
        if (exception.errorCode.status.is5xxServerError) {
            log.error("", exception)
        } else {
            log.error("Error Message = {}", exception.message)
        }
    }
}
