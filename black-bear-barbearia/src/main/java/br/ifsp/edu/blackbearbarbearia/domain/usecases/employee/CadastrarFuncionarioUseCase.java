package br.ifsp.edu.blackbearbarbearia.domain.usecases.employee;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityAlreadyExistsException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

import java.time.DayOfWeek;
import java.util.List;

public class CadastrarFuncionarioUseCase {
    private final UserDAO dao;
    public CadastrarFuncionarioUseCase(UserDAO dao) {
        this.dao = dao;
    }

    public Integer create(User employee) {
        Validator<User> validator = new CreateEmployeeInputRequestValidator();
        Notification notification = validator.validate(employee);

        if (notification.hasErros()) throw new IllegalArgumentException(notification.errorMessage());

        String email = employee.getEmail();
        if (dao.findOneByEmail(email).isPresent()) throw new EntityAlreadyExistsException("This email is already in use");

        String login = employee.getLogin();
        if (dao.findOneByLogin(login).isPresent()) throw new EntityAlreadyExistsException("This login is already in use");

        List<DayOfWeek> daysEmployee = employee.getDays();
        if (daysEmployee.stream().filter(day -> dao.findOneByDay(day).size() == 3).count() == 1) throw new IllegalArgumentException("Days with work limit reached");

        employee.setLastPassword(employee.getPasswordHash());
        return dao.create(employee);
    }
}
