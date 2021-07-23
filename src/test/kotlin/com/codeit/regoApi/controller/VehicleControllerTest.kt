package com.codeit.regoApi.controller

import com.codeit.regoApi.config.ApiKeyVerificationFilter.Companion.API_KEY_HEADER
import com.codeit.regoApi.model.Person
import com.codeit.regoApi.model.Vehicle
import com.codeit.regoApi.repository.PersonRepository
import com.codeit.regoApi.repository.VehicleRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class VehicleControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val personRepository: PersonRepository,
    @Autowired val vehicleRepository: VehicleRepository,
    @Value("\${api.key}") val apiKey: String
) {
    @Test
    fun `createVehicle should create and return new Vehicle`() {
        //given
        val rego = "rego200"
        val make = "make200"
        val model = "model200"

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/vehicle")
                .param("rego", rego)
                .param("make", make)
                .param("model", model)
                .header(API_KEY_HEADER, apiKey)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("rego").value(rego))
            .andExpect(MockMvcResultMatchers.jsonPath("make").value(make))
            .andExpect(MockMvcResultMatchers.jsonPath("model").value(model))
            .andExpect(MockMvcResultMatchers.jsonPath("owner").isEmpty)
    }

    @Test
    fun `createVehicle should return 400 when Vehicle already exists`() {
        //given
        val rego = "rego400"
        val make = "make400"
        val model = "model400"

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/vehicle")
                .param("rego", rego)
                .param("make", make)
                .param("model", model)
                .header(API_KEY_HEADER, apiKey)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/vehicle")
                .param("rego", rego)
                .param("make", make)
                .param("model", model)
                .header(API_KEY_HEADER, apiKey)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `linkUnlinkVehicleWithPerson should return 200 when unlinked Vehicle is linked`() {
        //given
        val rego = "regoLink200Unlinked"
        val make = "make"
        val model = "model"
        val firstName = "firstName200Unlinked"
        val lastName = "lastName200Unlinked"
        val address = "address"
        vehicleRepository.save(Vehicle(rego, make, model))
        val person = personRepository.save(Person(firstName = firstName, lastName = lastName, address = address))

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/vehicle")
                .param("rego", rego)
                .param("personId", person.id.toString())
                .header(API_KEY_HEADER, apiKey)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("rego").value(rego))
            .andExpect(MockMvcResultMatchers.jsonPath("make").value(make))
            .andExpect(MockMvcResultMatchers.jsonPath("model").value(model))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.owner.id").value(person.id.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.owner.firstName").value(person.firstName))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.owner.lastName").value(person.lastName))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.owner.address").value(person.address))
    }

    @Test
    fun `linkUnlinkVehicleWithPerson should return 200 when linked Vehicle is unlinked`() {
        //given
        val rego = "regoUnlink200Linked"
        val make = "make"
        val model = "model"
        val firstName = "firstUnlink200Linked"
        val lastName = "lastUnlink200Linked"
        val address = "address"
        val person = personRepository.save(Person(firstName = firstName, lastName = lastName, address = address))
        vehicleRepository.save(Vehicle(rego, make, model, person))

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/vehicle")
                .param("rego", rego)
                .param("personId", null)
                .header(API_KEY_HEADER, apiKey)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("rego").value(rego))
            .andExpect(MockMvcResultMatchers.jsonPath("make").value(make))
            .andExpect(MockMvcResultMatchers.jsonPath("model").value(model))
            .andExpect(MockMvcResultMatchers.jsonPath("owner").isEmpty)
    }

    @Test
    fun `linkUnlinkVehicleWithPerson should return 200 when linked Vehicle is relinked`() {
        //given
        val rego = "regoRelink200Linked"
        val make = "make"
        val model = "model"
        val firstName = "firstRelink200Linked"
        val lastName = "lastRelink200Linked"
        val address = "address"
        val firstNameNew = "firstRelink200LinkedNew"
        val lastNameNew = "lastRelink200LinkedNew"
        val addressNew = "addressNew"
        val person = personRepository.save(Person(firstName = firstName, lastName = lastName, address = address))
        val personNew =
            personRepository.save(Person(firstName = firstNameNew, lastName = lastNameNew, address = addressNew))
        vehicleRepository.save(Vehicle(rego, make, model, person))

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/vehicle")
                .param("rego", rego)
                .param("personId", personNew.id.toString())
                .header(API_KEY_HEADER, apiKey)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("rego").value(rego))
            .andExpect(MockMvcResultMatchers.jsonPath("make").value(make))
            .andExpect(MockMvcResultMatchers.jsonPath("model").value(model))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.owner.id").value(personNew.id.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.owner.firstName").value(personNew.firstName))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.owner.lastName").value(personNew.lastName))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.owner.address").value(personNew.address))
    }
}
