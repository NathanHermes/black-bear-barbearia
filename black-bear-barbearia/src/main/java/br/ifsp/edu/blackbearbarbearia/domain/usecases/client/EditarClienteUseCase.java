package br.ifsp.edu.blackbearbarbearia.domain.usecases.client;

import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

public class EditarClienteUseCase {
    private ClientDao dao;

    public EditarClienteUseCase(ClientDao dao) {
        this.dao = dao;
    }

    public boolean update(Client client) {
        Validator<Client> validator = new ClientInputRequestValidator();
        Notification notification = validator.validate(client);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        String id = client.getId();
        if (dao.findOne(id) == null)
            throw new EntityNotFoundException("Client not found.");

        return dao.update(client);
    }
}
