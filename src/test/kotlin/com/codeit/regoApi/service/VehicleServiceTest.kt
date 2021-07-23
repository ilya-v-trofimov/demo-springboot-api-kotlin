package com.codeit.regoApi.service

import com.codeit.regoApi.exception.ExistingVehicleException
import com.codeit.regoApi.model.Vehicle
import com.codeit.regoApi.repository.VehicleRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import java.util.Optional

internal class VehicleServiceTest {
    @Test
    fun `createVehicle should create and return new Vehicle`() {
        //given
        val mockRepository = Mockito.mock(VehicleRepository::class.java)
        val service = VehicleService(mockRepository)
        val rego = "rego"
        val make = "make"
        val model = "model"
        val expectedVehicle = Vehicle(rego, make, model)
        Mockito.`when`(mockRepository.findById(rego))
            .thenReturn(Optional.empty())
        Mockito.`when`(mockRepository.save(Mockito.any(Vehicle::class.java)))
            .thenReturn(expectedVehicle)

        //when
        val actualVehicle = service.createVehicle(rego, make, model)

        //then
        Mockito.verify(mockRepository).findById(rego)
        Mockito.verify(mockRepository).save(Vehicle(rego, make, model))
        assertEquals(expectedVehicle, actualVehicle)
    }

    @Test
    fun `createVehicle should throw an exception if Vehicle already exists`() {
        //given
        val mockRepository = Mockito.mock(VehicleRepository::class.java)
        val service = VehicleService(mockRepository)
        val rego = "rego"
        val make = "make"
        val model = "model"
        val existingVehicle = Vehicle(rego, make, model)
        Mockito.`when`(mockRepository.findById(rego))
            .thenReturn(Optional.of(existingVehicle))

        //when
        //then
        assertThrows<ExistingVehicleException> {
            service.createVehicle(rego, make, model)
        }
    }
}
