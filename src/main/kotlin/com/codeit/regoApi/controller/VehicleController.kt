package com.codeit.regoApi.controller

import com.codeit.regoApi.model.Vehicle
import com.codeit.regoApi.service.VehicleService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/vehicle")
@Api(value = "vehicle", description = "Vehicle related operations")
class VehicleController(val vehicleService: VehicleService) {
    @ApiOperation(value = "Create a vehicle", response = Vehicle::class)
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Vehicle successfully created"),
            ApiResponse(code = 400, message = "Vehicle already exists"),
            ApiResponse(code = 403, message = "Invalid API Key"),
        ]
    )
    @PostMapping("")
    fun createVehicle(
        @ApiParam(required = true)
        @RequestParam
        rego: String,
        @ApiParam(required = false)
        @RequestParam
        make: String?,
        @ApiParam(required = false)
        @RequestParam
        model: String?,
    ): Vehicle {
        return vehicleService.createVehicle(rego, make, model)
    }

    @PutMapping("")
    @ApiOperation(value = "Link/unlink a vehicle with a person", response = Vehicle::class)
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "The vehicle has been successfully updated"),
            ApiResponse(code = 404, message = "Vehicle with provided rego does not exit in the system"),
            ApiResponse(code = 403, message = "Invalid API Key"),
        ]
    )
    fun linkUnlinkVehicleWithPerson(
        @ApiParam(required = true)
        @RequestParam
        rego: String,
        @ApiParam(required = false, type = "UUID", example = "aa9dcce8-8b9f-40ba-ac71-3566fe8a1eee")
        @RequestParam
        personId: UUID?,
    ): Vehicle {
        return vehicleService.linkUnlinkVehicleWithPerson(rego, personId)
    }
}
