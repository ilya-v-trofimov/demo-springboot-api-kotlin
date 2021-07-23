package com.codeit.regoApi.service

import com.codeit.regoApi.exception.ExistingVehicleException
import com.codeit.regoApi.model.Vehicle
import com.codeit.regoApi.repository.VehicleRepository
import org.springframework.stereotype.Service

@Service
class VehicleService(val vehicleRepository: VehicleRepository) {
    fun createVehicle(rego: String, make: String?, model: String?): Vehicle {
        val existingVehicle = vehicleRepository.findById(rego)
        if (existingVehicle.isPresent) {
            throw ExistingVehicleException("Vehicle with rego '$rego' already exists")
        } else {
            val newVehicleRequest = Vehicle(
                rego = rego,
                make = make,
                model = model,
            )
            return vehicleRepository.save(newVehicleRequest)
        }
    }
}
