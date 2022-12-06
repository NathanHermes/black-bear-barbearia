package br.ifsp.edu.blackbearbarbearia.domain.usecases.employee;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

public class TrocaSenhaUseCase {
    private final UserDAO dao;
    public TrocaSenhaUseCase(UserDAO dao) {
        this.dao = dao;
    }

    public void modifyPassword(User user, String lastPassword, String newPassword, String confirmPassword) {
        if (user.getPasswordHash() != null) throw new IllegalArgumentException("To change the password it needs to be deleted by the administrator");

        Validator<User> validator = new UpdateEmployeeInputResquestValidator();
        Notification notification = validator.validate(lastPassword, newPassword, confirmPassword);

        if (notification.hasErros()) throw new IllegalArgumentException(notification.errorMessage());

        if (!newPassword.equals(confirmPassword)) throw new IllegalArgumentException("New password is not the same as confirm new password");
        //if (user.isValidPassword(lastPassword)) throw new IllegalArgumentException("Invalid last password");

        //user.setPasswordHash(newPassword);

        dao.update(user);
    }
}
