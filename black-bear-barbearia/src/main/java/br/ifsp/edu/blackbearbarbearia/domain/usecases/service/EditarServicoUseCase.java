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

    public void update(Integer serviceId, Service serviceUpdate) {
        Validator<Service> validator = new UpdateServiceInputRequestValidator();
        Notification notification = validator.validate(serviceUpdate);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        if(dao.findOne(serviceId).isEmpty())
            throw new EntityNotFoundException("Service not found");

        Service service = dao.findOne(serviceId).get();

        service.setComissionPercentage(serviceUpdate.getComissionPercentage());
        service.setTaxPercentage(serviceUpdate.getTaxPercentage());
        service.setActive(serviceUpdate.getActive());
        service.getTypes().clear();
        serviceUpdate.getTypes().forEach(service::addType);

        dao.update(service);
    }
}
