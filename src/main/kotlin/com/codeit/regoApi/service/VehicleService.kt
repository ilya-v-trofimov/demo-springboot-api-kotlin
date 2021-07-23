package com.codeit.regoApi.service

import com.codeit.regoApi.exception.ExistingVehicleException
import com.codeit.regoApi.exception.PersonNotFound
import com.codeit.regoApi.exception.VehicleNotFound
import com.codeit.regoApi.model.Vehicle
import com.codeit.regoApi.repository.PersonRepository
import com.codeit.regoApi.repository.VehicleRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class VehicleService(
    val vehicleRepository: VehicleRepository,
    val personRepository: PersonRepository,
) {
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

    fun linkUnlinkVehicleWithPerson(rego: String, personId: UUID?): Vehicle {
        val person = if (personId == null) {
            null
        } else {
            personRepository.findById(personId)
                .orElseThrow { throw PersonNotFound("Person not found") }
        }
        val vehicle = vehicleRepository.findById(rego)
            .orElseThrow { throw VehicleNotFound("Vehicle not found") }
        vehicle.owner = person
        return vehicleRepository.save(vehicle)
    }
}
