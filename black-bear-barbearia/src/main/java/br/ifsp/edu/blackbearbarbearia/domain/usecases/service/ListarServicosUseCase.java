package br.ifsp.edu.blackbearbarbearia.domain.usecases.service;

import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;

import java.util.List;
import java.util.Optional;

public class ListarServicosUseCase {
    private final ServiceDAO dao;

    public ListarServicosUseCase(ServiceDAO dao) {
        this.dao = dao;
    }

    public List<Service> findAll() {
        List<Service> services = dao.findAll();

        if (services.isEmpty())
            throw new IllegalArgumentException("Services not found");

        return services;
    }

    public Optional<Service> findOne(Integer serviceId) {
        if (dao.findOne(serviceId).isEmpty()) throw new IllegalArgumentException("Service not found");

        return dao.findOne(serviceId);
    }
}
