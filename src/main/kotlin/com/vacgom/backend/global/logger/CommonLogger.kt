package com.vacgom.backend.global.logger

import org.slf4j.LoggerFactory

abstract class CommonLogger {
    val log = LoggerFactory.getLogger(this.javaClass)!!
}
