package br.ifsp.edu.blackbearbarbearia.domain.usecases.user;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

public class LoginUseCase {
    private UserDao dao;

    public LoginUseCase(UserDao dao) {
        this.dao = dao;
    }
    public User login(String login, String password) {
        Validator<User> validator = new UseInputRequestValidator();
        Notification notification = validator.validate(login, password);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        if (dao.findOneByLogin(login) == null)
            throw new EntityNotFoundException("Login not found.");

        User user = dao.findOneByLogin(login);

        if (!password.equals(user.getPasswordHash()))
            throw new IllegalArgumentException("Invalid password.");
        return user;
    }
}
