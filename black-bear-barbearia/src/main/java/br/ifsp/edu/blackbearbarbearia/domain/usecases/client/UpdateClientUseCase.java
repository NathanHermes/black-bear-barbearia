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

    public boolean update(Integer id, Client clientUpdate) {
        Validator<Client> validator = new ClientInputRequestValidator();
        Notification notification = validator.validate(clientUpdate);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        if (dao.findOne(id).isEmpty())
            throw new EntityNotFoundException("Client not found.");

        Client client = dao.findOne(id).get();

        client.setName(clientUpdate.getName());
        client.setPhone(clientUpdate.getPhone());
        client.setEmail(clientUpdate.getEmail());

        return dao.update(client);
    }
}
