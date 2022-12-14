package br.ifsp.edu.blackbearbarbearia.domain.usecases.service;

import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityAlreadyExistsException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

public class CreateServiceUseCase {
    private final ServiceDAO dao;

    public CreateServiceUseCase(ServiceDAO dao) {
        this.dao = dao;
    }

    public Boolean create(Service service) {
        Validator<Service> validator = new CreateServiceInputRequestValidator();
        Notification notification = validator.validate(service);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        String name = service.getName();
        if (dao.findOneByName(name).isPresent())
            throw new EntityAlreadyExistsException("Name is already in use");

        return dao.create(service);
    }
}
