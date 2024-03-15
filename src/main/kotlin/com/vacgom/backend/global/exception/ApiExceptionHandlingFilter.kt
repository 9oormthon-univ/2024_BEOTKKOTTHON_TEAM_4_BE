package com.vacgom.backend.global.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.global.exception.error.ErrorResponse
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


@Component
class ApiExceptionHandlingFilter(
        private val om: ObjectMapper
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            chain: FilterChain
    ) {
        try {
            chain.doFilter(request, response)
        } catch (exception: BusinessException) {
            setErrorResponse(response, exception)
        }
    }

    private fun setErrorResponse(
            response: HttpServletResponse,
            exception: BusinessException
    ) = try {
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpStatus.UNAUTHORIZED.value()
        om.writeValue(response.outputStream, ErrorResponse(exception.errorCode))
    } catch (exception: IOException) {
        throw RuntimeException(exception)
    }
}
