package br.ifsp.edu.blackbearbarbearia.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class CadastrarServicoController {

    @FXML
    private RadioButton rbSim;

    @FXML
    private Label lblCadastrar;

    @FXML
    private TextField txtTaxa;

    @FXML
    private TextField txtComissao;

    @FXML
    private Button btnCadastrar;

    @FXML
    private RadioButton rbNao;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPreco;

    @FXML
    private Label lblStatus;

    @FXML
    private ComboBox<?> cbTipo;

    @FXML
    void cadastrar(ActionEvent event) {

    }

}
