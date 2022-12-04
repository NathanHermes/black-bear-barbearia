package br.ifsp.edu.blackbearbarbearia.domain.usecases.user;

import java.time.DayOfWeek;
import java.util.Optional;

public interface DayDAO {
    Optional<DayOfWeek> findOne(Integer key);
    Optional<Integer> findOne(DayOfWeek entity);
}
