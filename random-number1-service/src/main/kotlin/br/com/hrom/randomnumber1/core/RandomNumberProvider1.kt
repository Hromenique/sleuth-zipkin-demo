package br.com.hrom.randomnumber1.core

import mu.KotlinLogging
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class RandomNumberProvider1 {
    private val log = KotlinLogging.logger { }
    fun getNumber(): Int {
        log.info("Getting a random number")
        return Random.nextInt()
                .also { log.info("Returning number $it")  }
    }
}