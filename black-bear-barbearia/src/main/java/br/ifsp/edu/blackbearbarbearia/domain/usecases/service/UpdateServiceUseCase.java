package br.ifsp.edu.blackbearbarbearia.domain.usecases.service;

import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.ServiceBuilder;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

public class UpdateServiceUseCase {
    private final ServiceDAO dao;

    public UpdateServiceUseCase(ServiceDAO dao) {
        this.dao = dao;
    }

    public Boolean update(Integer serviceId, Service serviceUpdate) {
        Validator<Service> validator = new UpdateServiceInputRequestValidator();
        Notification notification = validator.validate(serviceUpdate);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        if(dao.findOne(serviceId).isEmpty())
            throw new EntityNotFoundException("Service not found");

        ServiceBuilder serviceBuilder = new ServiceBuilder();
        serviceBuilder.setId(serviceId);
        serviceBuilder.setName(serviceUpdate.getName());
        serviceBuilder.setPrice(serviceUpdate.getPrice());
        serviceBuilder.setComissionPercentage(serviceUpdate.getComissionPercentage());
        serviceBuilder.setTaxPercentage(serviceUpdate.getTaxPercentage());
        serviceBuilder.setActive(serviceUpdate.getActive());
        serviceBuilder.setTypes(serviceUpdate.getTypes());

        return dao.update(serviceBuilder.getResult());
    }
}
