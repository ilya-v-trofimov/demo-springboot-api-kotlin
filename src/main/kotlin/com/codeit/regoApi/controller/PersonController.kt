package com.codeit.regoApi.controller

import com.codeit.regoApi.model.Person
import com.codeit.regoApi.service.PersonService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/person")
@Api(value = "person", description = "Person related operations")
class PersonController(val personService: PersonService) {
    @PostMapping("")
    @ApiOperation(value = "Create a person")
    fun createPerson(
        @RequestParam firstName: String,
        @RequestParam lastName: String,
        @RequestParam address: String?,
    ): Person {
        return personService.createPerson(firstName, lastName, address)
    }
}
