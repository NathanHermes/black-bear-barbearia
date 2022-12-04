package br.ifsp.edu.blackbearbarbearia.domain.usecases.service;

import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Type;

import java.util.List;

public interface ServiceTypeDAO {
    void create(Integer serviceId, List<Type> types);
    Boolean update(Integer serviceId, List<Type> types);
    Boolean deleteByServiceId(Integer serviceId);
    List<Type> findByServiceId(Integer serviceId);
}
