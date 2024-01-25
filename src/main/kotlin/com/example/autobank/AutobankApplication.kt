package com.example.autobank

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AutobankApplication

fun main(args: Array<String>) {
    runApplication<AutobankApplication>(*args)
}
