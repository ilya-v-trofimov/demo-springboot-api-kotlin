package com.codeit.regoApi.config

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
class SecurityConfigTest(
    @Autowired val mockMvc: MockMvc,
    @Value("\${api.key}") val apiKey: String
) {
    @Test
    fun `calls without API Key should return 403`() {
        //given
        val firstName = "firstName403"
        val lastName = "lastName403"
        val address = "address403"

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/person")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("address", address)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun `calls with wrong API Key should return 403`() {
        //given
        val firstName = "firstName403"
        val lastName = "lastName403"
        val address = "address403"

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/person")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("address", address)
                .header(API_KEY_HEADER, "some-random-key")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isForbidden)
    }
}
