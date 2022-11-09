package br.ifsp.edu.blackbearbarbearia.domain.usecases.employee;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Address;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

import java.util.Optional;

public class EditarFuncionarioUseCase {
    private final UserDAO dao;
    public EditarFuncionarioUseCase(UserDAO dao) {
        this.dao = dao;
    }

    public void update(Integer employeeId, User employeeUpdate) {
        Validator<User> validator = new UpdateEmployeeInputResquestValidator();
        Notification notification = validator.validate(employeeUpdate);

        if (notification.hasErros()) throw new IllegalArgumentException(notification.errorMessage());

        if (dao.findOne(employeeId).isEmpty())
            throw new EntityNotFoundException("User not found");

        User employee = dao.findOne(employeeId).get();

        employee.setEmail(employeeUpdate.getEmail());
        employee.setPhone(employeeUpdate.getPhone());
        employee.setActive(employeeUpdate.isActive());

        Address employeeAddress = employeeUpdate.getAddress();
        employee.getAddress().setAddress(employeeAddress.getAddress());
        employee.getAddress().setNumber(employeeAddress.getNumber());
        employee.getAddress().setDistrict(employeeAddress.getDistrict());
        employee.getAddress().setCity(employeeAddress.getCity());

        dao.update(employee);
    }
}
