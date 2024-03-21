package com.vacgom.backend.global.config

import com.vacgom.backend.global.exception.ApiExceptionHandlingFilter
import com.vacgom.backend.global.security.filter.JwtAuthFilter
import com.vacgom.backend.global.security.matcher.CustomRequestMatcher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
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
        private val jwtAuthFilter: JwtAuthFilter,
        private val apiExceptionHandlingFilter: ApiExceptionHandlingFilter
) {

    @Bean
    @Order(0)
    fun authFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.securityMatchers { matcher -> matcher.requestMatchers(customRequestMatcher.authEndpoints()) }
        http.authorizeHttpRequests { auth -> auth.anyRequest().permitAll() }
        http.addFilterBefore(apiExceptionHandlingFilter, UsernamePasswordAuthenticationFilter::class.java)

        return commonHttpSecurity(http).build()
    }

    @Bean
    @Order(1)
    fun tempUserFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeRequests { auth ->
            auth.requestMatchers(customRequestMatcher.tempUserEndpoints()).hasRole("TEMP_USER").anyRequest().hasRole("USER")
        }
                .addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
                .addFilterBefore(apiExceptionHandlingFilter, UsernamePasswordAuthenticationFilter::class.java)
        return commonHttpSecurity(http).build()
    }

    @Bean
    @Order(2)
    fun anyRequestFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeRequests { auth ->
            auth.requestMatchers(customRequestMatcher.userEndpoints()).hasRole("USER")
        }
                .addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
                .addFilterBefore(apiExceptionHandlingFilter, UsernamePasswordAuthenticationFilter::class.java)
        return commonHttpSecurity(http).build()
    }

    private fun commonHttpSecurity(http: HttpSecurity): HttpSecurity {
        return http
                .csrf { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() }
                .cors { corsConfigurationSource() }
                .formLogin { formLogin: FormLoginConfigurer<HttpSecurity> -> formLogin.disable() }
                .httpBasic { basic: HttpBasicConfigurer<HttpSecurity> -> basic.disable() }
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
