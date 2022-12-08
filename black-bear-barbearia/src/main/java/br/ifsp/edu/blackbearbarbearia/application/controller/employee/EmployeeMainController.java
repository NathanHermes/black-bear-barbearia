package br.ifsp.edu.blackbearbarbearia.application.controller.employee;

import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Address;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.*;

public class EmployeeMainController {
    @FXML
    private TextField inputEmail;
    @FXML
    private TableView<User> tbvEmployees;
    @FXML
    private TableColumn<User, Integer> cID;
    @FXML
    private TableColumn<User, String> cFullName;
    @FXML
    private TableColumn<User, String> cEmail;
    @FXML
    private TableColumn<User, String> cPhone;
    @FXML
    private TableColumn<User, Address> cAddress;
    @FXML
    private TableColumn<User, Boolean> cStatus;

    private ObservableList<User> employeeData;

    @FXML
    private void initialize() {
        setValueSourceToColumns();
        setItemListToTBV();
        loadEmployeeData();
    }

    private void loadEmployeeData() {
        try {
            var employees = findEmployeeUseCase.findAll();
            employeeData.clear();
            employeeData.setAll(employees);
        } catch (EntityNotFoundException exception) {
            employeeData.clear();
        }
    }

    private void setItemListToTBV() {
        employeeData = FXCollections.observableArrayList();
        tbvEmployees.setItems(employeeData);
    }

    private void setValueSourceToColumns() {
        cID.setCellValueFactory(new PropertyValueFactory<>("id"));
        cFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("active"));
    }

    @FXML
    public void search() {
        String email = inputEmail.getText();

        if (email == null || email.isBlank())
            loadEmployeeData();
        else {
            User client = findEmployeeUseCase.findByEmail(email);
            employeeData.clear();
            employeeData.add(client);
        }
    }

    @FXML
    public void reloadTableView() {
        loadEmployeeData();
    }

    @FXML
    void save() {
        try {
            WindowLoader.setRoot("CreateOrUpdateEmployee");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void update() {
        User employeeSelected = tbvEmployees.getSelectionModel().getSelectedItem();
        if (employeeSelected != null) {
            try {
                WindowLoader.setRoot("CreateOrUpdateEmployee");
                CreateOrUpdateEmployeeController controller = (CreateOrUpdateEmployeeController) WindowLoader.getController();
                controller.setEmployee(employeeSelected);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void deletePassword() {
        Integer employeeID = tbvEmployees.getSelectionModel().getSelectedItem().getId();

        try {
            deletePasswordEmployeeUseCase.deletePassword(employeeID);
        } catch (EntityNotFoundException | IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @FXML
    public void back() {
        try {
            WindowLoader.setRoot("BookingMain");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

