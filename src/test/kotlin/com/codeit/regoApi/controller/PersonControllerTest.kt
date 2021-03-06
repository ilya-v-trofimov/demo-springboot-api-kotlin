package com.codeit.regoApi.controller

import com.codeit.regoApi.config.ApiKeyVerificationFilter.Companion.API_KEY_HEADER
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
class PersonControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Value("\${api.key}") val apiKey: String
) {
    @Test
    fun `createPerson should create and return new Person`() {
        //given
        val firstName = "firstName200"
        val lastName = "lastName200"
        val address = "address200"

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/person")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("address", address)
                .header(API_KEY_HEADER, apiKey)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("id").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("firstName").value(firstName))
            .andExpect(MockMvcResultMatchers.jsonPath("lastName").value(lastName))
            .andExpect(MockMvcResultMatchers.jsonPath("address").value(address))
    }

    @Test
    fun `createPerson should return 400 when Person already exists`() {
        //given
        val firstName = "firstName400"
        val lastName = "lastName400"
        val address = "address400"

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/person")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("address", address)
                .header(API_KEY_HEADER, apiKey)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/person")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("address", address)
                .header(API_KEY_HEADER, apiKey)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }
}
