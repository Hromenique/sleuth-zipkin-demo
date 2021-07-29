package br.com.hrom.randomnumber1.api

import br.com.hrom.randomnumber1.core.RandomNumberProvider1
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/random-number-1")
class RandomNumber1Controller(private val randomNumberProvider: RandomNumberProvider1) {

    @GetMapping(path=["/number"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getRandomNumber1() = RandomNumberResponse(randomNumberProvider.getNumber())
}

data class RandomNumberResponse(val number: Int)