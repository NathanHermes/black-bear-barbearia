package br.ifsp.edu.blackbearbarbearia.application.controller.booking;

import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.util.stream.Collectors;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.*;

public class CreateBookingController {

    @FXML
    private Label lblCadastrar;

    @FXML
    private ComboBox<String> cbCliente;

    @FXML
    private ComboBox<String> cbServico;

    @FXML
    private Button btnCadastrar;

    @FXML
    private ComboBox<String> cbFuncionario;

    @FXML
    private ComboBox<String> cbHorario;

    @FXML
    private Label txtValor;

    @FXML
    private DatePicker dtData;

    @FXML
    void cadastrar(ActionEvent event) {

    }

    @FXML
    void back(ActionEvent event) {

    }

    @FXML
    private void initialize() {
        setItemsCbFuncionario();
        setItemsCbServico();
        setItemsCbCliente();
    }

    private void setItemsCbCliente() {
        var clients = findClientUseCase.findAll();
        var names = clients.stream()
                .map(Client::getName)
                .collect(Collectors.toList());

        cbCliente.setItems(FXCollections.observableList(names));
    }

    private void setItemsCbServico() {
        var services = findServiceUseCase.findAll();
        var names = services.stream()
                .map(Service::getName)
                .collect(Collectors.toList());

        cbServico.setItems(FXCollections.observableList(names));
    }

    private void setItemsCbFuncionario() {
        var employees = findEmployeeUseCase.findAll();
        var names = employees.stream()
                .map(User::getFullName)
                .collect(Collectors.toList());

        cbFuncionario.setItems(FXCollections.observableList(names));
    }
}


