package br.edu.ifsp.domain.usecase.utils;

import java.util.List;
import java.util.Optional;

public interface Dao <T, K>{
    K create(T type);

    Optional<T> findOne(K key);

    List<T> findAll();

    boolean update(T type);

    boolean deleteByKey(K key);

    boolean delete(T type);

}
