package br.ifsp.edu.blackbearbarbearia.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class HomeAgendamentoController {

    @FXML
    private Label lblServico;

    @FXML
    private Label lblHorario;

    @FXML
    private TableColumn<?, ?> cPago;

    @FXML
    private ComboBox<?> cbServico;

    @FXML
    private Button btnCadastrar;

    @FXML
    private TableColumn<?, ?> cServico;

    @FXML
    private TableColumn<?, ?> cStatus;

    @FXML
    private TableColumn<?, ?> cCliente;

    @FXML
    private Label lblFuncionario;

    @FXML
    private TextField txtHorario;

    @FXML
    private Button btnServicosPrestados;

    @FXML
    private Label lblCliente;

    @FXML
    private Button btnFiltrar;

    @FXML
    private Button btnConcluir;

    @FXML
    private Button btnGerenciarClientes;

    @FXML
    private Button btnGerenciarFuncionarios;

    @FXML
    private Button btnDesmarcar;

    @FXML
    private Button btnLogout;

    @FXML
    private TableColumn<?, ?> cFuncionario;

    @FXML
    private Button btnNotaFiscal;

    @FXML
    private Button btnRelatorio;

    @FXML
    private TableView<?> tableView;

    @FXML
    private ComboBox<?> cbFuncionario;

    @FXML
    private Button btnGerenciarServicos;

    @FXML
    private Label lblData;

    @FXML
    private ComboBox<?> cbCliente;

    @FXML
    private DatePicker dtData;

    @FXML
    private TableColumn<?, ?> cData;

    @FXML
    void relatorio(ActionEvent event) {

    }

    @FXML
    void notaFiscal(ActionEvent event) {

    }

    @FXML
    void cadastrar(ActionEvent event) {

    }

    @FXML
    void desmarcar(ActionEvent event) {

    }

    @FXML
    void concluir(ActionEvent event) {

    }

    @FXML
    void filtrar(ActionEvent event) {

    }

    @FXML
    void servicosPrestados(ActionEvent event) {

    }

    @FXML
    void gerenciarServicos(ActionEvent event) {

    }

    @FXML
    void gerenciarClientes(ActionEvent event) {

    }

    @FXML
    void gerenciarFuncionario(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }

}

