package br.ifsp.edu.blackbearbarbearia.domain.usecases.employee;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.ConverterSenhaParaMD5;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

public class UpdatePasswordUseCase {
    private final UserDAO dao;
    public UpdatePasswordUseCase(UserDAO dao) {
        this.dao = dao;
    }

    public Boolean modifyPassword(String login, String newPassword, String confirmPassword) {
        Validator<User> validator = new UpdateEmployeeInputResquestValidator();
        Notification notification = validator.validate(login, newPassword, confirmPassword);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        if (dao.findByLogin(login).isEmpty())
            throw new EntityNotFoundException("Login not found");

        User employee = dao.findByLogin(login).get();
        if (!employee.getPasswordHash().isBlank())
            throw new IllegalArgumentException("To change the password it needs to be deleted by the administrator");

        if (!newPassword.equals(confirmPassword))
            throw new IllegalArgumentException("New password is not the same as confirm new password");

        String password = ConverterSenhaParaMD5.converterSenhaParaMD5(newPassword);
        return dao.updatePassword(login, password);
    }
}
