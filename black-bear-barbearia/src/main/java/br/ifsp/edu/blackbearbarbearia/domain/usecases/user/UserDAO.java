package br.ifsp.edu.blackbearbarbearia.domain.usecases.user;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.DAO;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface UserDAO extends DAO<User, Integer> {
    Optional<User> findOneByLogin(String login);
    Optional<User> findOneByEmail(String email);
    List<User> findOneByDay(DayOfWeek day);
}
