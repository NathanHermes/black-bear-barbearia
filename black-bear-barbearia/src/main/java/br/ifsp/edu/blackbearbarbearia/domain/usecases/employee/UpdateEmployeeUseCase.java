package br.ifsp.edu.blackbearbarbearia.domain.usecases.employee;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityAlreadyExistsException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

public class UpdateEmployeeUseCase {
    private final UserDAO dao;
    public UpdateEmployeeUseCase(UserDAO dao) {
        this.dao = dao;
    }

    public Boolean update(Integer employeeId, User employeeUpdate) {
        Validator<User> validator = new CreateEmployeeInputRequestValidator();
        Notification notification = validator.validate(employeeUpdate);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        if (dao.findOne(employeeId).isEmpty())
            throw new EntityNotFoundException("User not found");

        User employee = dao.findOne(employeeId).get();
        String email = employeeUpdate.getEmail();
        if (!employee.getEmail().equals(email) && dao.findByEmail(email).isPresent())
            throw new EntityAlreadyExistsException("This email is already in use");

        return dao.update(employeeUpdate);
    }
}
