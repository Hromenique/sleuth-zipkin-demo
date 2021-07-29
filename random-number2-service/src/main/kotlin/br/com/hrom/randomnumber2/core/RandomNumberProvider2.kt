package br.com.hrom.randomnumber2.core

import mu.KotlinLogging
import kotlin.random.Random

class RandomNumberProvider2 {
    private val log = KotlinLogging.logger { }

    fun getNumber(): Int {
        log.info("Getting a random number")
        return Random.nextInt()
                .also { log.info("Returning number $it")  }
    }
}