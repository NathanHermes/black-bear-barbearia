package br.ifsp.edu.blackbearbarbearia.domain.usecases.service;

import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Type;

import java.util.Optional;

public interface TypeDAO {
    Optional<Type> findOne(Integer key);
    Optional<Integer> findId(Type entity);
}
