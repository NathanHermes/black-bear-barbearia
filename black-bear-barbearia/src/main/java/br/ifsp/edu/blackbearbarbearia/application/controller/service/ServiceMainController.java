package br.ifsp.edu.blackbearbarbearia.application.controller.service;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ServiceMainController {

    @FXML
    private TableColumn<?, ?> cPreco;

    @FXML
    private Label lblPesquisar;

    @FXML
    private TableColumn<?, ?> cTaxa;

    @FXML
    private TableColumn<?, ?> cNome;

    @FXML
    private TableColumn<?, ?> cAtivo;

    @FXML
    private TextField inputBusca;

    @FXML
    private TableView<?> tbvService;

    @FXML
    private TableColumn<?, ?> cTipo;

    @FXML
    private Button btnEditar;

    @FXML
    private TableColumn<?, ?> cComissao;

    @FXML
    private TableColumn<?, ?> cID;

    @FXML
    void search(ActionEvent event) {

    }

    @FXML
    void update(ActionEvent event) {

    }

    @FXML
    void save(ActionEvent event) {

    }

    @FXML
    void reloadTableView(ActionEvent event) {

    }

    @FXML
    void back(ActionEvent event) {

    }
}


