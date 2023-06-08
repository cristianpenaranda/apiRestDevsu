package com.cristianpenaranda.apiRestDevsu.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MovimientoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllMovimiento() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/movimiento/listar")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                                                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}