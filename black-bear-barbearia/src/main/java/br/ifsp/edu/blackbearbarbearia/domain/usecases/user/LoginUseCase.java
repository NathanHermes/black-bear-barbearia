package br.ifsp.edu.blackbearbarbearia.domain.usecases.user;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.ConverterSenhaParaMD5;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

public class LoginUseCase {
    private final UserDAO dao;

    public LoginUseCase(UserDAO dao) {
        this.dao = dao;
    }

    public User login(User user) {
        Validator<User> validator = new UserInputRequestValidator();
        Notification notification = validator.validate(user);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        String login = user.getLogin();
        if (dao.findByLogin(login).isEmpty())
            throw new EntityNotFoundException("Login not found");
        User userDAO = dao.findByLogin(login).get();
        String password = ConverterSenhaParaMD5.converterSenhaParaMD5(user.getPasswordHash());

        if (userDAO.getPasswordHash() != null && !password.equals(userDAO.getPasswordHash()))
            throw new IllegalArgumentException("Invalid password");

        return userDAO;
    }
}
