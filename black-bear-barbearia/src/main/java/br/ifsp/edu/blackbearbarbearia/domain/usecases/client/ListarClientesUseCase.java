package br.ifsp.edu.blackbearbarbearia.domain.usecases.client;

import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;

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

    public Client findOne(Integer id) {
        if (dao.findOne(id).isEmpty())
            throw new EntityNotFoundException("Client not found");

        return dao.findOne(id).get();
    }
}
