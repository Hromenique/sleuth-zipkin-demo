package br.com.hrom.randomsum.api

import br.com.hrom.randomsum.core.RandomSumProvider
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/random-sum")
class RandomSumController(private val randomSumProvider: RandomSumProvider) {

    @GetMapping(path = ["/two-numbers"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    suspend fun getRandomSumOfTwoNumbers() = SumResultResponse(randomSumProvider.doSumOfTwoRandomNumbers())
}

data class SumResultResponse(val result: Int)