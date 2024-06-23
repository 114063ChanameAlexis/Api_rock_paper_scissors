package com.example.demo.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.demo.models.Player;
import com.example.demo.services.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(PlayerController.class)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlayerService playerService;

    @Test
    public void getByIdTest() throws Exception{

        Player player = new Player();
        player.setId(1L);
        player.setEmail("Alexischaname20@gmail.com");
        player.setUsername("chana95");
        player.setPassword("Password03#");
        when(playerService.getPlayerById(1L)).thenReturn(player);
        this.mockMvc.perform(get("/players/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("chana95"))
                .andExpect(jsonPath("$.email").value("Alexischaname20@gmail.com"))
                .andExpect(jsonPath("$.password").value("Password03#"))
                ;

        MvcResult mvcResult = this.mockMvc.perform(get("/players/1")).andDo(print()).andExpect(status().isOk())
                .andReturn();
        Player result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Player.class);

        Assertions.assertEquals("chana95", result.getUsername());
    }
}
