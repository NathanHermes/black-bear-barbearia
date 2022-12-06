package br.ifsp.edu.blackbearbarbearia.domain.usecases.client;

import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

public class UpdateClientUseCase {
    private final ClientDAO dao;

    public UpdateClientUseCase(ClientDAO dao) {
        this.dao = dao;
    }

    public Boolean update(Integer id, Client client) {
        Validator<Client> validator = new ClientInputRequestValidator();
        Notification notification = validator.validate(client);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        if (dao.findOne(id).isEmpty())
            throw new EntityNotFoundException("Client not found.");

        Client daoClient = dao.findOne(id).get();

        daoClient.setName(client.getName());
        daoClient.setPhone(client.getPhone());
        daoClient.setEmail(client.getEmail());

        return dao.update(daoClient);
    }
}
