package br.ifsp.edu.blackbearbarbearia.application.controller.booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class CreateBookingController {

    @FXML
    private Label lblCadastrar;

    @FXML
    private ComboBox<?> cbCliente;

    @FXML
    private ComboBox<?> cbServico;

    @FXML
    private Button btnCadastrar;

    @FXML
    private ComboBox<?> cbFuncionario;

    @FXML
    private ComboBox<?> cbHorario;

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
}


