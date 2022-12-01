package br.ifsp.edu.blackbearbarbearia.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class HomeClientesController {

    @FXML
    private Label lblPesquisar;

    @FXML
    private Button btnLogout;

    @FXML
    private TableColumn<?, ?> cNome;

    @FXML
    private TableColumn<?, ?> cTelefone;

    @FXML
    private TableColumn<?, ?> cEmail;

    @FXML
    private Button btnFiltrar;

    @FXML
    private Button btnCadastrar;

    @FXML
    private TextField txtPesquisar;

    @FXML
    private TableView<?> tableView;

    @FXML
    private Button btnEditar;

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void cadastrar(ActionEvent event) {

    }

    @FXML
    void editar(ActionEvent event) {

    }

    @FXML
    void filtrar(ActionEvent event) {

    }

}

