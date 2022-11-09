package br.ifsp.edu.blackbearbarbearia.domain.usecases.user;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;

public class ApagarSenhaFuncionarioUseCase {
    private final UserDAO dao;
    public ApagarSenhaFuncionarioUseCase(UserDAO dao) {
        this.dao = dao;
    }

    public void deletePassword(Integer employeeId) {
        if (employeeId.compareTo(1) == 0) throw new IllegalArgumentException("Unable to delete admin password");

        if (dao.findOne(employeeId).isEmpty()) throw new EntityNotFoundException("User not found");

        User employee = dao.findOne(employeeId).get();
        if(employee.getPasswordHash() == null) throw new IllegalArgumentException("Password is already null");

        employee.clearPasswordHash();

        dao.update(employee);
    }
}
