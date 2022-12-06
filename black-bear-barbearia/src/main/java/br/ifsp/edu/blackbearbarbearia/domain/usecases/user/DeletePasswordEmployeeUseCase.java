package br.ifsp.edu.blackbearbarbearia.domain.usecases.user;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;

public class DeletePasswordEmployeeUseCase {
    private final UserDAO dao;
    public DeletePasswordEmployeeUseCase(UserDAO dao) {
        this.dao = dao;
    }

    public Boolean deletePassword(Integer employeeId) {
        if (dao.findOne(employeeId).isEmpty())
            throw new EntityNotFoundException("User not found");

        User employee = dao.findOne(employeeId).get();
        if(employee.getPasswordHash() == null)
            throw new IllegalArgumentException("Password is already null");

        employee.clearPasswordHash();

        return dao.update(employee);
    }
}
