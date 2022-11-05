package br.ifsp.edu.blackbearbarbearia.domain.usecases.service;

import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

public class EditarServicoUseCase {
    private final ServiceDAO dao;

    public EditarServicoUseCase(ServiceDAO dao) {
        this.dao = dao;
    }

    public boolean update(Service service) {
        Validator<Service> validator = new ServiceInputRequestValidator();
        Notification notification = validator.validate(service);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        String name = service.getNome();
        if(dao.findOneByName(name).isEmpty())
            throw new EntityNotFoundException(name);

        return dao.update(service);
    }
}
