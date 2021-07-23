package com.codeit.regoApi.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class VehicleControllerTest(@Autowired val mockMvc: MockMvc) {
    @Test
    fun `createVehicle should create and return new Vehicle`() {
        //given
        val rego = "rego1"
        val make = "make1"
        val model = "model1"

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/vehicle")
                .param("rego", rego)
                .param("make", make)
                .param("model", model)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("rego").value("rego1"))
            .andExpect(MockMvcResultMatchers.jsonPath("make").value("make1"))
            .andExpect(MockMvcResultMatchers.jsonPath("model").value("model1"))
    }
}
