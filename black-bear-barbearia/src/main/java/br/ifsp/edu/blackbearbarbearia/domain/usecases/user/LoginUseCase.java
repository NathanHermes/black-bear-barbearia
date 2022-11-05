package br.ifsp.edu.blackbearbarbearia.domain.usecases.user;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

import java.util.Optional;

public class LoginUseCase {
    private UserDAO dao;

    public LoginUseCase(UserDAO dao) {
        this.dao = dao;
    }
    public User login(String login, String password) {
        Validator<User> validator = new UseInputRequestValidator();
        Notification notification = validator.validate(login, password);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        if (dao.findOneByLogin(login) == null)
            throw new EntityNotFoundException("Login not found.");

        Optional<User> user = dao.findOneByLogin(login);

        if (!password.equals(user.get().getPasswordHash()))
            throw new IllegalArgumentException("Invalid password.");
        return user.get();
    }
}
