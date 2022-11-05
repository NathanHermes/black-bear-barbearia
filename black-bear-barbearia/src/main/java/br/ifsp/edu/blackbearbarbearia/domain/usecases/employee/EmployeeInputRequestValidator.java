package br.ifsp.edu.blackbearbarbearia.domain.usecases.employee;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

public class EmployeeInputRequestValidator extends Validator<User> {
    @Override
    public Notification validate(User employee) {
        Notification notification = new Notification();

        if (employee == null) {
            notification.addError("Employee is null");
            return notification;
        }

        if (nullOrEmpty(employee.getFullName())) notification.addError("Full name is null or empty.");
        if (nullOrEmpty(employee.getEmail())) notification.addError("Email is null or empty.");
        if (nullOrEmpty(employee.getPhone())) notification.addError("Phone is null or empty.");
        if (nullOrEmpty(employee.getAddress())) notification.addError("Address is null or empty.");
        if (nullOrEmpty(employee.getNumber())) notification.addError("Number is null or empty.");
        if (nullOrEmpty(employee.getDistrict())) notification.addError("District is null or empty.");
        if (nullOrEmpty(employee.getCity())) notification.addError("City is null or empty.");
        if (nullOrEmpty(employee.getLogin())) notification.addError("Login is null or empty.");
        if (nullOrEmpty(employee.getPasswordHash())) notification.addError("Password is null or empty.");

        return notification;
    }
}
