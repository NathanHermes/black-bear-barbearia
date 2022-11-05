package br.ifsp.edu.blackbearbarbearia.domain.usecases.client;

import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;

import java.util.List;

public class ListarClientesUseCase {
    private final ClientDAO dao;

    public ListarClientesUseCase(ClientDAO dao) {
        this.dao = dao;
    }

    public List<Client> findAll() {
        List<Client> clients = dao.findAll();

        if (clients.isEmpty())
            throw new IllegalArgumentException("No client found.");

        return clients;
    }
}
