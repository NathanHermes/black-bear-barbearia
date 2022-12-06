package br.ifsp.edu.blackbearbarbearia.domain.usecases.employee;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityAlreadyExistsException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

import java.time.DayOfWeek;
import java.util.List;

public class CreateEmployeeUseCase {
    private final UserDAO dao;
    public CreateEmployeeUseCase(UserDAO dao) {
        this.dao = dao;
    }

    public Boolean create(User employee) {
        Validator<User> validator = new CreateEmployeeInputRequestValidator();
        Notification notification = validator.validate(employee);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        String email = employee.getEmail();
        if (dao.findByEmail(email).isPresent())
            throw new EntityAlreadyExistsException("This email is already in use");

        String login = employee.getLogin();
        if (dao.findByLogin(login).isPresent())
            throw new EntityAlreadyExistsException("This login is already in use");

        List<DayOfWeek> days = employee.getDays();
        if (days.size() > 6)
            throw new IllegalArgumentException("Days with work limit reached");

        for (DayOfWeek day: days) {
            Integer countDay = dao.findCountByDay(day.getValue());

            if (countDay >= 3)
                throw new IllegalArgumentException(day + " with work limit reached");
        }

        return dao.create(employee);
    }
}
