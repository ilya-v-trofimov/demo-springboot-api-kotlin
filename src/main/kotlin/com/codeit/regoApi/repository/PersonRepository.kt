package com.codeit.regoApi.repository

import com.codeit.regoApi.model.Person
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface PersonRepository : CrudRepository<Person, UUID> {
    fun findByFirstNameAndLastName(firstName: String, lastName: String): Optional<Person>
}
