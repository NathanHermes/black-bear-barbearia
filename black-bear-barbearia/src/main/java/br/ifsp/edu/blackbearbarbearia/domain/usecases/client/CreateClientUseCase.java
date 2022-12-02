package br.ifsp.edu.blackbearbarbearia.domain.usecases.client;

import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityAlreadyExistsException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

public class CreateClientUseCase {
    private final ClientDAO dao;

    public CreateClientUseCase(ClientDAO dao) {
        this.dao = dao;
    }

    public Integer create(Client client) {
        Validator<Client> validator = new ClientInputRequestValidator();
        Notification notification = validator.validate(client);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        String email = client.getEmail();
        if(dao.findOneByEmail(email).isPresent())
            throw new EntityAlreadyExistsException("This e-mail is already in use.");

        return dao.create(client);
    }
}
