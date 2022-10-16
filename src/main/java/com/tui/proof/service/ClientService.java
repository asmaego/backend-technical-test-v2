package com.tui.proof.service;

import com.tui.proof.model.Client;
import com.tui.proof.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client getClient(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

}
