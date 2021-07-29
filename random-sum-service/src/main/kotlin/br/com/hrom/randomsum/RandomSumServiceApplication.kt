package br.com.hrom.randomsum

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RandomSumServiceApplication

fun main(args: Array<String>) {
	runApplication<RandomSumServiceApplication>(*args)
}
