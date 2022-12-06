package br.ifsp.edu.blackbearbarbearia.domain.usecases.user;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

import java.util.Optional;

public class LoginUseCase {
    private final UserDAO dao;

    public LoginUseCase(UserDAO dao) {
        this.dao = dao;
    }

    public User login(User user) {
        Validator<User> validator = new UserInputRequestValidator();
        Notification notification = validator.validate(user);

        if (notification.hasErros()) throw new IllegalArgumentException(notification.errorMessage());

        Optional<User> userDAO = dao.findByLogin(user.getLogin());
        if (userDAO.isEmpty()) throw new EntityNotFoundException("Login not found");


        if (userDAO.get().getPasswordHash() != null && !user.getPasswordHash().equals(userDAO.get().getPasswordHash()))
            throw new IllegalArgumentException("Invalid password");

        return userDAO.get();
    }
}
