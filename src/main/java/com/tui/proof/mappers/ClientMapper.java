package com.tui.proof.mappers;

import com.tui.proof.dto.client.ClientDTO;
import com.tui.proof.model.Client;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ClientMapper {

    public ClientDTO clientToClientDto(Client client) {
        if (Objects.isNull(client)) {
            return null;
        }
        return ClientDTO.builder()
                .id(client.getId())
                .email(client.getEmail())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .telephone(client.getTelephone())
                .build();
    }

    public List<ClientDTO> clientListToClientListDto(List<Client> client) {
        if (Objects.isNull(client)) {
            return null;
        }
        return client.stream().map(this::clientToClientDto).collect(Collectors.toList());
    }

    public Client clientDtoToClient(ClientDTO clientDto) {
        if (Objects.isNull(clientDto)) {
            return null;
        }

        return Client.builder()
                .id(clientDto.getId())
                .email(clientDto.getEmail())
                .firstName(clientDto.getFirstName())
                .lastName(clientDto.getLastName())
                .telephone(clientDto.getTelephone())
                .build();
    }
}
