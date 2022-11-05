package br.ifsp.edu.blackbearbarbearia.domain.usecases.client;

import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.DAO;

import java.util.Optional;

public interface ClientDAO extends DAO<Client, Integer> {
    Optional<Client> findOneByEmail(String email);
}
