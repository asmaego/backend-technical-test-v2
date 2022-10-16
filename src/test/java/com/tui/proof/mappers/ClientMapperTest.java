package com.tui.proof.mappers;

import com.tui.proof.dto.client.ClientDTO;
import com.tui.proof.model.Client;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientMapperTest {

    ClientMapper clientMapper = new ClientMapper();

    @Test
    void clientToClientDto_when_not_null() {
        Client client = new Client(1L, "fname", "lname", "0123456", "aa@a", null);

        ClientDTO dto = clientMapper.clientToClientDto(client);
        assertEquals(1L,dto.getId());
        assertEquals("fname",dto.getFirstName());
        assertEquals("lname",dto.getLastName());
        assertEquals("0123456",dto.getTelephone());
        assertEquals("aa@a",dto.getEmail());
    }

    @Test
    void clientToClientDto_when_null() {
        ClientDTO dto = clientMapper.clientToClientDto(null);
        assertNull(dto);
    }

    @Test
    void clientListToClientListDto_when_not_null() {
        List<Client> clientList= new ArrayList<>();
        clientList.add(new Client(1L, "fname", "lname", "0123456", "aa@a", null));

        List<ClientDTO> clientListDto= clientMapper.clientListToClientListDto(clientList);
        assertEquals(1,clientListDto.size());
        assertEquals(1L,clientListDto.get(0).getId());
        assertEquals("fname",clientListDto.get(0).getFirstName());
        assertEquals("lname",clientListDto.get(0).getLastName());
        assertEquals("0123456",clientListDto.get(0).getTelephone());
        assertEquals("aa@a",clientListDto.get(0).getEmail());
    }

    @Test
    void clientListToClientListDto_when_null() {
        List<ClientDTO> clientListDto= clientMapper.clientListToClientListDto(null);
        assertNull(clientListDto);
    }

    @Test
    void clientDtoToClient_when_not_null() {
        ClientDTO dto = new ClientDTO(1L, "fname", "lname", "aa@a", "0123456");

        Client client = clientMapper.clientDtoToClient(dto);
        assertEquals(1L,client.getId());
        assertEquals("fname",client.getFirstName());
        assertEquals("lname",client.getLastName());
        assertEquals("0123456",client.getTelephone());
        assertEquals("aa@a",client.getEmail());
    }

    @Test
    void clientDtoToClient_when_null() {
        Client client = clientMapper.clientDtoToClient(null);
        assertNull(client);
    }
}