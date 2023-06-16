package com.example.dankapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DankAppApplication

fun main(args: Array<String>) {
	runApplication<DankAppApplication>(*args)
}
