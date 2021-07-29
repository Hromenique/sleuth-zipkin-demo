package br.com.hrom.randomnumber2.api

import br.com.hrom.randomnumber2.core.RandomNumberProvider2
import io.javalin.Javalin

class RandomNumber2Controller(
        private val randomNumberProvider: RandomNumberProvider2,
        private val app: Javalin
) {

    init {
        initGetRandomNumber()
    }

    private fun initGetRandomNumber() {
        app.get("/random-number-2/number") { ctx ->
            ctx.json(RandomNumberResponse(randomNumberProvider.getNumber()))
        }
    }
}

data class RandomNumberResponse(val number: Int)