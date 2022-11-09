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

    public void update(User lastEmployee, User employee) {
        Address employeeAddress = employee.getAddress();

        if (!employee.getEmail().isBlank() && employee.getEmail() != null)
            lastEmployee.setEmail(employee.getEmail());
        if (!employee.getPhone().isBlank() && employee.getPhone() != null)
            lastEmployee.setPhone(employee.getPhone());
        if (!employeeAddress.getAddress().isBlank() && employeeAddress.getAddress() != null)
            lastEmployee.getAddress().setAddress(employeeAddress.getAddress());
        if (!employeeAddress.getNumber().isBlank() && employeeAddress.getNumber() != null)
            lastEmployee.getAddress().setNumber(employeeAddress.getNumber());
        if (!employeeAddress.getDistrict().isBlank() && employeeAddress.getDistrict() != null)
            lastEmployee.getAddress().setDistrict(employeeAddress.getDistrict());
        if (!employeeAddress.getCity().isBlank() && employeeAddress.getCity() != null)
            lastEmployee.getAddress().setCity(employeeAddress.getCity());
        if (employee.isActive() != null)
            lastEmployee.setActive(employee.isActive());

        dao.update(lastEmployee);
    }
}
