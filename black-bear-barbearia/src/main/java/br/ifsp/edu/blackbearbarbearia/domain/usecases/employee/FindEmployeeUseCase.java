package br.ifsp.edu.blackbearbarbearia.domain.usecases.employee;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;

import java.util.List;

public class FindEmployeeUseCase {
    private final UserDAO dao;

    public FindEmployeeUseCase(UserDAO dao) {
        this.dao = dao;
    }

    public User findOne(Integer id) {
        if (dao.findOne(id).isEmpty())
            throw new EntityNotFoundException("Employee not found");

        return dao.findOne(id).get();
    }
    public List<User> findAll() {
        if (dao.findAll().isEmpty())
            throw new EntityNotFoundException("Employees not found.");

        return dao.findAll();
    }

    public User findByEmail(String email) {
        if (dao.findByEmail(email).isEmpty())
            throw new EntityNotFoundException("Employee not found");

        return dao.findByEmail(email).get();
    }
}
