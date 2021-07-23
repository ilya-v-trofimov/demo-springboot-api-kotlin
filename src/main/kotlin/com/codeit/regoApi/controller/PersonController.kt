package com.codeit.regoApi.controller

import com.codeit.regoApi.model.Person
import com.codeit.regoApi.service.PersonService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/person")
@Api(value = "person", description = "Person related operations")
class PersonController(val personService: PersonService) {
    @PostMapping("")
    @ApiOperation(
        value = "Create a person",
        response = Person::class
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Person successfully created"),
            ApiResponse(code = 400, message = "Person already exists"),
            ApiResponse(code = 403, message = "Invalid API Key"),
        ]
    )
    fun createPerson(
        @ApiParam(required = true)
        @RequestParam
        firstName: String,
        @ApiParam(required = true)
        @RequestParam
        lastName: String,
        @ApiParam(required = false)
        @RequestParam
        address: String?,
    ): Person {
        return personService.createPerson(firstName, lastName, address)
    }
}
