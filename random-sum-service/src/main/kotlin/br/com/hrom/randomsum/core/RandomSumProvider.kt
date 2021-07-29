package br.com.hrom.randomsum.core

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class RandomSumProvider(
        private val number1Provider: RandomNumber1Provider,
        private val number2Provider: RandomNumber2Provider
) {
    private val log = KotlinLogging.logger { }

    suspend fun doSumOfTwoRandomNumbers() = coroutineScope {
        log.info("Starting sum of two random numbers")

        val n1 = async { number1Provider.getNumber() }
        val n2 = async { number2Provider.getNumber() }

        (n1.await() + n2.await())
                .also { log.info("Returning random sum $it") }
    }
}