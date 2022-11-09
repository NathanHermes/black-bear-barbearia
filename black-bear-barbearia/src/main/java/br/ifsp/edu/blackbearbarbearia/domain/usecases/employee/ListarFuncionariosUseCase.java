package br.ifsp.edu.blackbearbarbearia.domain.usecases.employee;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;

import java.util.List;
import java.util.Optional;

public class ListarFuncionariosUseCase {
    private final UserDAO dao;

    public ListarFuncionariosUseCase(UserDAO dao) {
        this.dao = dao;
    }

    public Optional<User> findOne(Integer id) {
        return dao.findOne(id);
    }
    public List<User> findAll() {
        List<User> users = dao.findAll();

        if (users.isEmpty())
            throw new IllegalArgumentException("No user found.");

        return users;
    }
}
