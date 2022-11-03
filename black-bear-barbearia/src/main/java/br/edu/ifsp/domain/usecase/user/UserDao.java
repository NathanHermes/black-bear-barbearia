package br.edu.ifsp.domain.usecase.user;

import br.edu.ifsp.domain.entities.user.User;
import br.edu.ifsp.domain.usecase.utils.Dao;

import java.util.Optional;

public interface UserDao extends Dao<User, Integer> {
    User findOneByLogin(String login);
}
