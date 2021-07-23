package com.codeit.regoApi.service

import com.codeit.regoApi.exception.ExistingPersonException
import com.codeit.regoApi.model.Person
import com.codeit.regoApi.repository.PersonRepository
import org.springframework.stereotype.Service

@Service
class PersonService(private val personRepository: PersonRepository) {
    fun createPerson(firstName: String, lastName: String, address: String?): Person {
        val existingPerson = personRepository.findByFirstNameAndLastName(firstName, lastName)
        if (existingPerson.isPresent) {
            throw ExistingPersonException("Person with first name '$firstName' and last name '$lastName' already exists")
        } else {
            val newPersonRequest = Person(
                firstName = firstName,
                lastName = lastName,
                address = address
            )
            return personRepository.save(newPersonRequest)
        }
    }
}
