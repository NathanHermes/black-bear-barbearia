package br.ifsp.edu.blackbearbarbearia.domain.usecases.user;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.DAO;

import java.util.Optional;

public interface UserDAO extends DAO<User, Integer> {
    Optional<User> findByLogin(String login);
    Optional<User> findByEmail(String email);
    Integer findByDay(Integer day);
}
