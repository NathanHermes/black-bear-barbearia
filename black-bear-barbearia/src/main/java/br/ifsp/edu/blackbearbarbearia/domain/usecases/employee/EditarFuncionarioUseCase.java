package br.ifsp.edu.blackbearbarbearia.domain.usecases.employee;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

public class EditarFuncionarioUseCase {
    private final UserDAO dao;

    public EditarFuncionarioUseCase(UserDAO dao) {
        this.dao = dao;
    }

    public boolean update(User employee) {
        Validator<User> validator = new EmployeeInputRequestValidator();
        Notification notification = validator.validate(employee);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        String login = employee.getLogin();
        if (dao.findOneByLogin(login).isEmpty())
            throw new EntityNotFoundException(login);

        return dao.update(employee);
    }
}
