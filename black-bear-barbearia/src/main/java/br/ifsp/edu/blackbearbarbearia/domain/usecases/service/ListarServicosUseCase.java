package br.ifsp.edu.blackbearbarbearia.domain.usecases.service;

import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;

import java.util.List;

public class ListarServicosUseCase {
    private final ServiceDAO dao;

    public ListarServicosUseCase(ServiceDAO dao) {
        this.dao = dao;
    }

    public List<Service> findAll() {
        List<Service> services = dao.findAll();

        if (services.isEmpty())
            throw new IllegalArgumentException("No service found.");

        return services;
    }
}
