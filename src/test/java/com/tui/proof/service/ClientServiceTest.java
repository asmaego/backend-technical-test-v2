package com.tui.proof.service;

import com.tui.proof.model.Client;
import com.tui.proof.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ClientServiceTest {

    ClientService clientService;
    @Mock
    ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        clientService = new ClientService(clientRepository);
    }

    @Test
    void getClient() {

        when(clientRepository.findById(any())).thenReturn(Optional.of(new Client(111L, "fname", "lname", "0123456", "aa@a", Collections.emptyList())));

        Client client = clientService.getClient(111L);
        assertNotNull(client);
        assertEquals(111L, client.getId());
        assertEquals("fname", client.getFirstName());
        assertEquals("lname", client.getLastName());
        assertEquals("0123456", client.getTelephone());
        assertEquals("aa@a", client.getEmail());

    }

    @Test
    void getClient_null() {
        when(clientRepository.findById(any())).thenReturn(Optional.empty());
        assertNull(clientService.getClient(111L));
    }
}