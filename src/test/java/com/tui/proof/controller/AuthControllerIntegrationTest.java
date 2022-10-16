package com.tui.proof.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tui.proof.dto.login.LoginDTO;
import com.tui.proof.security.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AuthControllerIntegrationTest {

    MockMvc mockMvc;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider tokenProvider;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AuthController(authenticationManager, tokenProvider)).build();
    }

    @Test
    void authenticateUser() throws Exception {
        LoginDTO dto = new LoginDTO("admin", "password");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(dto);

        mockMvc.perform(post("/auth/signin").contentType(APPLICATION_JSON_UTF8).content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    void authenticateUser_bad_credentials() throws JsonProcessingException {
        LoginDTO dto = new LoginDTO("admin", "passwor");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(dto);

        Throwable exception = assertThrows(NestedServletException.class, () -> mockMvc.perform(post("/auth/signin").contentType(APPLICATION_JSON_UTF8).content(requestJson)));
        assertEquals("Request processing failed; nested exception is org.springframework.security.authentication.BadCredentialsException: Bad credentials", exception.getMessage());
        ;
    }
}