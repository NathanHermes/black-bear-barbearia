package br.ifsp.edu.blackbearbarbearia.usecases.user;

import br.ifsp.edu.blackbearbarbearia.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.usecases.utils.Dao;

public interface UserDao extends Dao<User, Integer> {
    User findOneByLogin(String login);
}
