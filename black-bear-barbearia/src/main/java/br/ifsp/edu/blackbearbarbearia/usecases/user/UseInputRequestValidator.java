package br.ifsp.edu.blackbearbarbearia.usecases.user;

import br.ifsp.edu.blackbearbarbearia.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.usecases.utils.Validator;

public class UseInputRequestValidator extends Validator<User> {
    @Override
    public Notification validate(User user) {
        Notification notification = new Notification();

        if (user == null) {
            notification.addError("User is null");
            return notification;
        }

        if (nullOrEmpty(user.getLogin()))
            notification.addError("Login is null or empty");
        if (nullOrEmpty(user.getPasswordHash()))
            notification.addError("Password is null or empty");

        return notification;
    }

    @Override
    public Notification validate(String login, String password) {
        Notification notification = new Notification();

        if (nullOrEmpty(login))
            notification.addError("Login is null or empty");
        if (nullOrEmpty(password))
            notification.addError("Password is null or empty");

        return notification;
    }
}
