package br.ifsp.edu.blackbearbarbearia.domain.usecases.employee;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityAlreadyExistsException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

public class CadastrarFuncionarioUseCase {
    private final UserDAO dao;
    public CadastrarFuncionarioUseCase(UserDAO dao) {
        this.dao = dao;
    }

    public Integer create(User employee) {
        Validator<User> validator = new EmployeeInputRequestValidator();
        Notification notification = validator.validate(employee);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        String login = employee.getLogin();
        if (dao.findOneByLogin(login).isPresent())
            throw new EntityAlreadyExistsException("This login is already in use.");

        return dao.create(employee);
    }
}
