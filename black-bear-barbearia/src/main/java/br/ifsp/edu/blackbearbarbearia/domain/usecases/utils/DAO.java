package br.ifsp.edu.blackbearbarbearia.domain.usecases.utils;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DAO<T, K>{
    Boolean create(T type);
    Boolean update(T type);
    Boolean deleteByKey(K key);
    Boolean delete(T type);
    Optional<T> findOne(K key);
    List<T> findAll();
}
