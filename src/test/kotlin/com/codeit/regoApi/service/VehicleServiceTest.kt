package com.codeit.regoApi.service

import com.codeit.regoApi.exception.ExistingVehicleException
import com.codeit.regoApi.model.Person
import com.codeit.regoApi.model.Vehicle
import com.codeit.regoApi.repository.PersonRepository
import com.codeit.regoApi.repository.VehicleRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import java.util.Optional
import java.util.UUID

internal class VehicleServiceTest {
    @Test
    fun `createVehicle should create and return new Vehicle`() {
        //given
        val mockVehicleRepository = Mockito.mock(VehicleRepository::class.java)
        val mockPersonRepository = Mockito.mock(PersonRepository::class.java)
        val service = VehicleService(mockVehicleRepository, mockPersonRepository)
        val rego = "rego"
        val make = "make"
        val model = "model"
        val expectedVehicle = Vehicle(rego, make, model)
        Mockito.`when`(mockVehicleRepository.findById(rego))
            .thenReturn(Optional.empty())
        Mockito.`when`(mockVehicleRepository.save(Mockito.any(Vehicle::class.java)))
            .thenReturn(expectedVehicle)

        //when
        val actualVehicle = service.createVehicle(rego, make, model)

        //then
        Mockito.verify(mockVehicleRepository).findById(rego)
        Mockito.verify(mockVehicleRepository).save(Vehicle(rego, make, model))
        assertEquals(expectedVehicle, actualVehicle)
    }

    @Test
    fun `createVehicle should throw an exception if Vehicle already exists`() {
        //given
        val mockVehicleRepository = Mockito.mock(VehicleRepository::class.java)
        val mockPersonRepository = Mockito.mock(PersonRepository::class.java)
        val service = VehicleService(mockVehicleRepository, mockPersonRepository)
        val rego = "rego"
        val make = "make"
        val model = "model"
        val existingVehicle = Vehicle(rego, make, model)
        Mockito.`when`(mockVehicleRepository.findById(rego))
            .thenReturn(Optional.of(existingVehicle))

        //when
        //then
        assertThrows<ExistingVehicleException> {
            service.createVehicle(rego, make, model)
        }
    }

    @Test
    fun `linkUnlinkVehicleWithPerson should link unlinked Vehicle`() {
        //given
        val mockVehicleRepository = Mockito.mock(VehicleRepository::class.java)
        val mockPersonRepository = Mockito.mock(PersonRepository::class.java)
        val service = VehicleService(mockVehicleRepository, mockPersonRepository)
        val vehicle = Vehicle("rego", "make", "model")
        val person = Person(
            id = UUID.randomUUID(),
            firstName = "firstName",
            lastName = "lastName",
            address = "address"
        )

        val expectedLinkedVehicle = vehicle.copy(owner = person)

        Mockito.`when`(mockVehicleRepository.findById(vehicle.rego))
            .thenReturn(Optional.of(vehicle))
        Mockito.`when`(mockPersonRepository.findById(person.id!!))
            .thenReturn(Optional.of(person))
        Mockito.`when`(mockVehicleRepository.save(expectedLinkedVehicle))
            .thenReturn(expectedLinkedVehicle)

        //when
        val actualLinkedVehicle = service.linkUnlinkVehicleWithPerson(vehicle.rego, person.id)

        //then
        Mockito.verify(mockVehicleRepository).findById(vehicle.rego)
        Mockito.verify(mockPersonRepository).findById(person.id!!)
        Mockito.verify(mockVehicleRepository).save(expectedLinkedVehicle)
        assertEquals(expectedLinkedVehicle, actualLinkedVehicle)
    }

    @Test
    fun `linkUnlinkVehicleWithPerson should unlink linked Vehicle`() {
        //given
        val mockVehicleRepository = Mockito.mock(VehicleRepository::class.java)
        val mockPersonRepository = Mockito.mock(PersonRepository::class.java)
        val service = VehicleService(mockVehicleRepository, mockPersonRepository)
        val person = Person(
            id = UUID.randomUUID(),
            firstName = "firstName",
            lastName = "lastName",
            address = "address"
        )
        val vehicle = Vehicle("rego", "make", "model", person)

        val expectedLinkedVehicle = vehicle.copy(owner = null)

        Mockito.`when`(mockVehicleRepository.findById(vehicle.rego))
            .thenReturn(Optional.of(vehicle))
        Mockito.`when`(mockVehicleRepository.save(expectedLinkedVehicle))
            .thenReturn(expectedLinkedVehicle)

        //when
        val actualLinkedVehicle = service.linkUnlinkVehicleWithPerson(vehicle.rego, null)

        //then
        Mockito.verify(mockVehicleRepository).findById(vehicle.rego)
        Mockito.verify(mockVehicleRepository).save(expectedLinkedVehicle)
        assertEquals(expectedLinkedVehicle, actualLinkedVehicle)
    }

    @Test
    fun `linkUnlinkVehicleWithPerson should relink linked Vehicle`() {
        //given
        val mockVehicleRepository = Mockito.mock(VehicleRepository::class.java)
        val mockPersonRepository = Mockito.mock(PersonRepository::class.java)
        val service = VehicleService(mockVehicleRepository, mockPersonRepository)
        val person = Person(
            id = UUID.randomUUID(),
            firstName = "firstName",
            lastName = "lastName",
            address = "address"
        )
        val vehicle = Vehicle("rego", "make", "model", person)

        val personNew = Person(
            id = UUID.randomUUID(),
            firstName = "firstNameNew",
            lastName = "lastNameNew",
            address = "addressNew"
        )

        val expectedLinkedVehicle = vehicle.copy(owner = personNew)

        Mockito.`when`(mockVehicleRepository.findById(vehicle.rego))
            .thenReturn(Optional.of(vehicle))
        Mockito.`when`(mockPersonRepository.findById(personNew.id!!))
            .thenReturn(Optional.of(personNew))
        Mockito.`when`(mockVehicleRepository.save(expectedLinkedVehicle))
            .thenReturn(expectedLinkedVehicle)

        //when
        val actualLinkedVehicle = service.linkUnlinkVehicleWithPerson(vehicle.rego, personNew.id)

        //then
        Mockito.verify(mockVehicleRepository).findById(vehicle.rego)
        Mockito.verify(mockPersonRepository).findById(personNew.id!!)
        Mockito.verify(mockVehicleRepository).save(expectedLinkedVehicle)
        assertEquals(expectedLinkedVehicle, actualLinkedVehicle)
    }
}
