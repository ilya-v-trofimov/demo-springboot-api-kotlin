package com.codeit.regoApi.controller

import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest(@Autowired val mockMvc: MockMvc) {
    @Test
    fun `createPerson should create and return new Person`() {
        //given
        val firstName = "firstName1"
        val lastName = "lastName1"
        val address = "address1"

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/person")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("address", address)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("id").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("firstName").value("firstName1"))
            .andExpect(MockMvcResultMatchers.jsonPath("lastName").value("lastName1"))
            .andExpect(MockMvcResultMatchers.jsonPath("address").value("address1"))
    }
}
