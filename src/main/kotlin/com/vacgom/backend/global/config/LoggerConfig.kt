package com.vacgom.backend.global.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LoggerConfig {

    @Bean
    fun logger(): Logger {
        return LoggerFactory.getLogger(this.javaClass)!!
    }
}
