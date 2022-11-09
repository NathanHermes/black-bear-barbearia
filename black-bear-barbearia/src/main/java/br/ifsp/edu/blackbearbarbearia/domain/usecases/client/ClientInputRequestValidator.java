package br.ifsp.edu.blackbearbarbearia.domain.usecases.client;

import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

public class ClientInputRequestValidator extends Validator<Client> {
    @Override
    public Notification validate(Client client) {
        Notification notification = new Notification();

        if (client == null) {
            notification.addError("Client is null");
            return notification;
        }

        if (nullOrEmpty(client.getName()))
            notification.addError("Name is null or empty");
        if (nullOrEmpty(client.getEmail()))
            notification.addError("E-mail is null or empty");
        if (nullOrEmpty(client.getPhone()))
            notification.addError("Phone is null or empty");

        return notification;
    }
}
