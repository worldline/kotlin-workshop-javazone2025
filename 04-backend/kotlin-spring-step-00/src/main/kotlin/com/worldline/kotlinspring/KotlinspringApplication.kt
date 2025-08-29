package com.worldline.kotlinspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinspringApplication

fun main(args: Array<String>) {
	runApplication<KotlinspringApplication>(*args)
}
