package br.ifsp.edu.blackbearbarbearia.domain.usecases.client;

import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;

import java.util.List;

public class FindClientUseCase {
    private final ClientDAO dao;

    public FindClientUseCase(ClientDAO dao) {
        this.dao = dao;
    }

    public List<Client> findAll() {
        if (dao.findAll().isEmpty())
            throw new EntityNotFoundException("No client found.");

        return dao.findAll();
    }

    public Client findOne(Integer id) {
        if (dao.findOne(id).isEmpty())
            throw new EntityNotFoundException("Client not found");

        return dao.findOne(id).get();
    }

    public Client findByEmail(String email) {
        if (dao.findOneByEmail(email).isEmpty())
            throw new EntityNotFoundException("Client not found");

        return dao.findOneByEmail(email).get();
    }
}
