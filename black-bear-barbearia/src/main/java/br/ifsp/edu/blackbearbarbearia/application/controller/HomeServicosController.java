package br.ifsp.edu.blackbearbarbearia.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class HomeServicosController {

    @FXML
    private TableColumn<?, ?> cPreco;

    @FXML
    private TableColumn<?, ?> cTaxa;

    @FXML
    private Button btnLogout;

    @FXML
    private TableColumn<?, ?> cNome;

    @FXML
    private Button btnCadastrar;

    @FXML
    private TableView<?> tbvService;

    @FXML
    private TableColumn<?, ?> cTipo;

    @FXML
    private Button btnEditar;

    @FXML
    private TableColumn<?, ?> cComissao;

    @FXML
    private Label lblPesquisar;

    @FXML
    private Button btnFiltrar;

    @FXML
    private TableColumn<?, ?> cAtivo;

    @FXML
    private TextField txtPesquisar;

    @FXML
    void filtrar(ActionEvent event) {

    }

    @FXML
    void editar(ActionEvent event) {

    }

    @FXML
    void cadastrar(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }

}
