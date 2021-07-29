package br.com.hrom.randomsum.core

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class RandomNumber2Provider(
        @Value("\${integrationServices.randomNumber2Service.getNumber.endpoint}")
        private val randomNumber2ApiEndpoint: String,
        private val webClient: WebClient
) {
    private val log = KotlinLogging.logger { }

    suspend fun getNumber(): Int {
        log.info("Get a random number")
        return webClient
                .get()
                .uri(randomNumber2ApiEndpoint)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .awaitBody<RandomNumber1Provider.Result>()
                .number
                .also { log.info("Returning $it") }
    }
}