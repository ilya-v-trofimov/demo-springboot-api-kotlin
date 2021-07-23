package com.codeit.regoApi.controller

import com.codeit.regoApi.model.Vehicle
import com.codeit.regoApi.service.VehicleService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/vehicle")
@Api(value = "vehicle", description = "Vehicle related operations")
class VehicleController(val vehicleService: VehicleService) {
    @PostMapping("")
    @ApiOperation(value = "Create a vehicle")
    fun createVehicle(
        @RequestParam rego: String,
        @RequestParam make: String?,
        @RequestParam model: String?,
    ): Vehicle {
        return vehicleService.createVehicle(rego, make, model)
    }
}
