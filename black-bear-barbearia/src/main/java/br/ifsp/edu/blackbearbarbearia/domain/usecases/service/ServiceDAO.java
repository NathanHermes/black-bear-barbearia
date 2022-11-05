package br.ifsp.edu.blackbearbarbearia.domain.usecases.service;

import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.DAO;

import java.util.Optional;

public interface ServiceDAO extends DAO<Service, Integer> {
    Optional<Service> findOneByName(String name);
}
