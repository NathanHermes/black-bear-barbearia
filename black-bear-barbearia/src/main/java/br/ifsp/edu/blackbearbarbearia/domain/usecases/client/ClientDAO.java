package br.ifsp.edu.blackbearbarbearia.domain.usecases.client;

import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.DAO;

public interface ClientDAO extends DAO<Client, Integer> {
    User findOneByEmail(String email);

    User findOne(String id);
}
