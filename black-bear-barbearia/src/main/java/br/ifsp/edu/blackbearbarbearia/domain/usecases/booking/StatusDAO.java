package br.ifsp.edu.blackbearbarbearia.domain.usecases.booking;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Status;

import java.util.Optional;

public interface StatusDAO {
    Optional<Status> findOne(Integer key);
    Optional<Integer> findId(Status entity);
}
