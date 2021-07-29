package br.com.hrom.randomsum.core

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class RandomNumber1Provider(
        @Value("\${integrationServices.randomNumber1Service.getNumber.endpoint}")
        private val randomNumber1ApiEndpoint: String,
        private val webClient: WebClient
) {
    private val log = KotlinLogging.logger { }

    suspend fun getNumber(): Int {
        log.info("Get a random number")
        return webClient
                .get()
                .uri(randomNumber1ApiEndpoint)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .awaitBody<Result>()
                .number
                .also { log.info("Returning $it") }
    }

    data class Result(val number: Int)
}

