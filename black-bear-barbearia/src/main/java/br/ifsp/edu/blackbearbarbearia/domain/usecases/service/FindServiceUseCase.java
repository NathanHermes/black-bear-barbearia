package br.ifsp.edu.blackbearbarbearia.domain.usecases.service;

import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;

import java.util.List;

public class FindServiceUseCase {
    private final ServiceDAO dao;

    public FindServiceUseCase(ServiceDAO dao) {
        this.dao = dao;
    }

    public List<Service> findAll() {
        List<Service> services = dao.findAll();

        if (services.isEmpty())
            throw new IllegalArgumentException("Services not found");

        return services;
    }

    public Service findOne(Integer serviceId) {
        if (dao.findOne(serviceId).isEmpty()) throw new IllegalArgumentException("Service not found");

        return dao.findOne(serviceId).get();
    }

    public Service findKeyByName(String serviceName) {
        if (dao.findOneByName(serviceName).isEmpty()) throw new IllegalArgumentException("Service not found");

        return dao.findOneByName(serviceName).get();
    }
}
