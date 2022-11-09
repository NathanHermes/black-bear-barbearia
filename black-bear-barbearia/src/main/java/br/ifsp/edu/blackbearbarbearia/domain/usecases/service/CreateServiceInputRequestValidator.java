package br.ifsp.edu.blackbearbarbearia.domain.usecases.service;

import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

public class CreateServiceInputRequestValidator extends Validator<Service> {
    @Override
    public Notification validate(Service service) {
        Notification notification = new Notification();

        if (service == null) {
            notification.addError("Service is null");
            return notification;
        }

        if (nullOrEmpty(service.getNome())) notification.addError("Name is null or empty");
        if (nullOrEmpty(String.valueOf(service.getPrice()))) notification.addError("Price is null or empty");
        if (nullOrEmpty(String.valueOf(service.getComissionPercentage()))) notification.addError("Commission is null or empty");
        if (nullOrEmpty(String.valueOf(service.getTaxPercentage()))) notification.addError("Taxa percentage is null or empty");
        if (nullOrEmpty(String.valueOf(service.getActive()))) notification.addError("Service status is null or empty");
        if (service.getTypes().isEmpty()) notification.addError("The type of this service is empty");

        return notification;
    }
}
