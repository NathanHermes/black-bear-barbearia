package br.ifsp.edu.blackbearbarbearia.domain.usecases.employee;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Address;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

public class UpdateEmployeeInputResquestValidator extends Validator<User> {
    @Override
    public Notification validate(User employee) {
        Notification notification = new Notification();

        if (employee == null) {
            notification.addError("Employee is null");
            return notification;
        }

        if (nullOrEmpty(employee.getEmail())) notification.addError("Email is null or empty");
        if (nullOrEmpty(employee.getPhone())) notification.addError("Phone is null or empty");
        if (employee.isActive() == null) notification.addError("Active is null");

        Address employeeAddress = employee.getAddress();
        if (nullOrEmpty(employeeAddress.getAddress())) notification.addError("Address is null or empty");
        if (nullOrEmpty(employeeAddress.getNumber())) notification.addError("Number is null or empty");
        if (nullOrEmpty(employeeAddress.getDistrict())) notification.addError("District is null or empty");
        if (nullOrEmpty(employeeAddress.getCity())) notification.addError("City is null or empty");

        return notification;
    }

    public Notification validate(String login, String newPassword, String confirmNewPassword) {
        Notification notification = new Notification();

        if (nullOrEmpty(login)) notification.addError("Login is null or empty");
        if (nullOrEmpty(newPassword)) notification.addError("New password is null or empty");
        if (nullOrEmpty(confirmNewPassword)) notification.addError("Confirm new password is null or empty");

        return notification;
    }
}
