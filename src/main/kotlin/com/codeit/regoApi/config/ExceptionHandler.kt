package com.codeit.regoApi.config

import com.codeit.regoApi.exception.ExistingPersonException
import com.codeit.regoApi.exception.ExistingVehicleException
import com.codeit.regoApi.exception.InvalidPersonDetails
import com.codeit.regoApi.exception.PersonNotFound
import com.codeit.regoApi.exception.VehicleNotFound
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(
        ExistingPersonException::class,
        ExistingVehicleException::class,
        InvalidPersonDetails::class,
    )
    fun handleBandRequest(e: Throwable): ResponseEntity<String> = ResponseEntity<String>(
        e.message,
        HttpStatus.BAD_REQUEST
    )

    @ExceptionHandler(
        PersonNotFound::class,
        VehicleNotFound::class,
    )
    fun handleNotFound(e: Throwable): ResponseEntity<String> = ResponseEntity<String>(
        e.message,
        HttpStatus.NOT_FOUND
    )
}
