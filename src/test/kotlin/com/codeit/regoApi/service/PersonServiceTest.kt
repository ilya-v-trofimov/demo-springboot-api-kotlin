package com.codeit.regoApi.service

import com.codeit.regoApi.exception.ExistingPersonException
import com.codeit.regoApi.model.Person
import com.codeit.regoApi.repository.PersonRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.util.Optional
import java.util.UUID

internal class PersonServiceTest {

    @Test
    fun `createPerson should create and return new Person`() {
        //given
        val mockRepository = mock(PersonRepository::class.java)
        val service = PersonService(mockRepository)
        val firstName = "firstName"
        val lastName = "lastName"
        val address = "address"
        val expectedPerson = Person(
            id = UUID.randomUUID(),
            firstName = firstName,
            lastName = lastName,
            address = address
        )
        `when`(mockRepository.findByFirstNameAndLastName(anyString(), anyString()))
            .thenReturn(Optional.empty())
        `when`(mockRepository.save(any(Person::class.java)))
            .thenReturn(expectedPerson)

        //when
        val actualPerson = service.createPerson(firstName, lastName, address)

        //then
        verify(mockRepository).findByFirstNameAndLastName(firstName, lastName)
        verify(mockRepository).save(Person(firstName = firstName, lastName = lastName, address = address))
        assertEquals(expectedPerson, actualPerson)
    }

    @Test
    fun `createPerson should throw an exception if Person already exists`() {
        //given
        val mockRepository = mock(PersonRepository::class.java)
        val service = PersonService(mockRepository)
        val firstName = "firstName"
        val lastName = "lastName"
        val address = "address"
        val existingPerson = Person(
            id = UUID.randomUUID(),
            firstName = firstName,
            lastName = lastName,
            address = address
        )
        `when`(mockRepository.findByFirstNameAndLastName(anyString(), anyString()))
            .thenReturn(Optional.of(existingPerson))

        //when
        //then
        assertThrows<ExistingPersonException> { service.createPerson(firstName, lastName, address) }
    }
}
