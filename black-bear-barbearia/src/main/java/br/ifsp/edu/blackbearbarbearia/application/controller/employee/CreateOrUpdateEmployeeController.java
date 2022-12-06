package br.ifsp.edu.blackbearbarbearia.application.controller.employee;

import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Address;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Role;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.UserBuilder;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityAlreadyExistsException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.*;

public class CreateOrUpdateEmployeeController {
    @FXML
    private Label lblTitle;
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputEmail;
    @FXML
    private TextField inputPhone;
    @FXML
    private TextField inputLogin;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private TextField inputAddress;
    @FXML
    private TextField inputNumber;
    @FXML
    private TextField inputDistrict;
    @FXML
    private TextField inputComplement;
    @FXML
    private TextField inputCity;
    @FXML
    private CheckBox ckMonday;
    @FXML
    private CheckBox ckTuesday;
    @FXML
    private CheckBox ckWednesday;
    @FXML
    private CheckBox ckThursday;
    @FXML
    private CheckBox ckFriday;
    @FXML
    private CheckBox ckSaturday;
    @FXML
    private CheckBox ckSunday;
    @FXML
    private RadioButton rbActive;
    @FXML
    private RadioButton rbDisable;
    @FXML
    private Button btnSaveOrUpdate;
    @FXML
    private Label lblResponseMessage;

    private User employee;

    @FXML
    private void initialize() {
        if (employee == null)
            clearInputs();
    }

    private void clearInputs() {
        lblTitle.setText("Cadastrar");
        btnSaveOrUpdate.setText("Cadastrar");
        inputName.setText("");
        inputEmail.setText("");
        inputPhone.setText("");
        inputLogin.setText("");
        inputPassword.setText("");
        inputAddress.setText("");
        inputNumber.setText("");
        inputDistrict.setText("");
        inputComplement.setText("");
        inputCity.setText("");
        ckMonday.setSelected(false);
        ckTuesday.setSelected(false);
        ckWednesday.setSelected(false);
        ckThursday.setSelected(false);
        ckFriday.setSelected(false);
        ckSaturday.setSelected(false);
        ckSunday.setSelected(false);
        rbActive.setDisable(true);
        rbDisable.setDisable(true);
    }

    public void setEmployee(User employeeSelected) {
        if (employeeSelected == null)
            throw new IllegalArgumentException("Employee can not be null");
        
        this.employee = employeeSelected;
        setInfoEmployeeIntoInputs();
    }

    private void setInfoEmployeeIntoInputs() {
        lblTitle.setText("Editar");
        btnSaveOrUpdate.setText("Editar");
        inputName.setText(employee.getFullName());
        inputName.setDisable(true);
        inputEmail.setText(employee.getEmail());
        inputPhone.setText(employee.getPhone());
        inputLogin.setText(employee.getLogin());
        inputLogin.setDisable(true);
        inputPassword.setText(employee.getPasswordHash());
        inputPassword.setDisable(true);

        Address address = employee.getAddress();
        inputAddress.setText(address.getAddress());
        inputNumber.setText(address.getNumber());
        inputDistrict.setText(address.getDistrict());
        inputComplement.setText(address.getComplement());
        inputCity.setText(address.getCity());

        List<DayOfWeek> days = employee.getDays();

        if (days.contains(DayOfWeek.MONDAY))
            ckMonday.setSelected(true);
        if (days.contains(DayOfWeek.TUESDAY))
            ckTuesday.setSelected(true);
        if (days.contains(DayOfWeek.WEDNESDAY))
            ckWednesday.setSelected(true);
        if (days.contains(DayOfWeek.THURSDAY))
            ckThursday.setSelected(true);
        if (days.contains(DayOfWeek.FRIDAY))
            ckFriday.setSelected(true);
        if (days.contains(DayOfWeek.SATURDAY))
            ckSaturday.setSelected(true);
        if (days.contains(DayOfWeek.SUNDAY))
            ckSunday.setSelected(true);

        ckMonday.setDisable(true);
        ckTuesday.setDisable(true);
        ckWednesday.setDisable(true);
        ckThursday.setDisable(true);
        ckFriday.setDisable(true);
        ckSaturday.setDisable(true);
        ckSunday.setDisable(true);

        if (employee.isActive())
            rbActive.setSelected(true);
        else
            rbDisable.setSelected(true);

        rbActive.setDisable(false);
        rbDisable.setDisable(false);
    }

    @FXML
    void saveOrUpdate() {
        lblResponseMessage.setText("");

        if (employee == null) {
            getInfoEmployeeFromInputs();

            try {
                Boolean response = createEmployeeUseCase.create(employee);
                if (response.equals(Boolean.TRUE))
                    lblResponseMessage.setText("Cliente cadastrado");
                else
                    lblResponseMessage.setText("Não foi possível cadastrar esse cliente.\nTente novamente mais tarde.");
            } catch (IllegalArgumentException | EntityAlreadyExistsException exception) {
                lblResponseMessage.setText(exception.getMessage());
            }
        } else {
            getInfoEmployeeFromInputs();

            try {
                Boolean response = updateEmployeeUseCase.update(employee.getId(), employee);
                if (response.equals(Boolean.TRUE))
                    lblResponseMessage.setText("Cliente atualizado");
                else
                    lblResponseMessage.setText("Não foi possível atualizar esse cliente.\nTente novamente mais tarde.");
            } catch (IllegalArgumentException | EntityNotFoundException exception) {
                lblResponseMessage.setText(exception.getMessage());
            }
        }
        employee = null;
    }

    private void getInfoEmployeeFromInputs() {
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.setFullName(inputName.getText());
        userBuilder.setEmail(inputEmail.getText());
        userBuilder.setPhone(inputPhone.getText());
        userBuilder.setLogin(inputLogin.getText());
        userBuilder.setPasswordHash(inputPassword.getText());

        Address address = new Address();
        address.setAddress(inputAddress.getText());
        address.setNumber(inputNumber.getText());
        address.setDistrict(inputDistrict.getText());
        address.setComplement(inputComplement.getText());
        address.setCity(inputCity.getText());
        userBuilder.setAddress(address);

        if (employee != null) {
            userBuilder.setId(employee.getId());

            if (rbActive.isSelected())
                userBuilder.setActive(Boolean.TRUE);
            else
                userBuilder.setActive(Boolean.FALSE);

            userBuilder.setDays(employee.getDays());
            userBuilder.setRole(employee.getRole());
        } else {
            List<DayOfWeek> days = new ArrayList<>();
            if (ckMonday.isSelected())
                days.add(DayOfWeek.MONDAY);
            if (ckTuesday.isSelected())
                days.add(DayOfWeek.TUESDAY);
            if (ckWednesday.isSelected())
                days.add(DayOfWeek.WEDNESDAY);
            if (ckThursday.isSelected())
                days.add(DayOfWeek.THURSDAY);
            if (ckFriday.isSelected())
                days.add(DayOfWeek.FRIDAY);
            if (ckSaturday.isSelected())
                days.add(DayOfWeek.SATURDAY);
            if (ckSunday.isSelected())
                days.add(DayOfWeek.SUNDAY);
            userBuilder.setDays(days);

            userBuilder.setActive(Boolean.TRUE);
            userBuilder.setRole(Role.EMPLOYEE);
        }
        employee = userBuilder.getResult();
    }

    @FXML
    public void back() {
        try {
            WindowLoader.setRoot("EmployeeMain");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

