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

    public User login(User user) {
        Validator<User> validator = new UseInputRequestValidator();
        Notification notification = validator.validate(user);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        User userDAO = dao.findOneByLogin(user.getLogin());
        if (userDAO == null)
            throw new EntityNotFoundException("Login not found.");

        if (!user.getPasswordHash().equals(userDAO.getPasswordHash()))
            throw new IllegalArgumentException("Invalid password.");
        return user;
    }
}
