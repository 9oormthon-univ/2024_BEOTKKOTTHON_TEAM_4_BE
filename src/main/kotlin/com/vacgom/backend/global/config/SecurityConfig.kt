package com.vacgom.backend.global.config

import com.vacgom.backend.global.exception.ApiExceptionHandlingFilter
import com.vacgom.backend.global.security.matcher.CustomRequestMatcher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
        private val customRequestMatcher: CustomRequestMatcher,
        private val apiExceptionHandlingFilter: ApiExceptionHandlingFilter
) {

    @Bean
    fun authFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.securityMatchers { matcher -> matcher.requestMatchers(customRequestMatcher.authEndpoints()) }
        http.authorizeHttpRequests { auth -> auth.anyRequest().permitAll() }
        http.addFilterBefore(apiExceptionHandlingFilter, UsernamePasswordAuthenticationFilter::class.java)

        return commonHttpSecurity(http).build()
    }


    @Throws(Exception::class)
    private fun commonHttpSecurity(http: HttpSecurity): HttpSecurity {
        return http
                .csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
                .cors { corsConfigurationSource() }
                .formLogin { obj: FormLoginConfigurer<HttpSecurity> -> obj.disable() }
                .httpBasic { obj: HttpBasicConfigurer<HttpSecurity> -> obj.disable() }
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        configuration.setAllowedOriginPatterns(listOf("*"))
        configuration.allowedMethods = listOf("HEAD", "POST", "GET", "DELETE", "PUT")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
