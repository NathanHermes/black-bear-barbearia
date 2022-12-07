package br.ifsp.edu.blackbearbarbearia.application.controller.booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ReportMainController {

    @FXML
    private RadioButton rbCSV;

    @FXML
    private ComboBox<?> cbServico;

    @FXML
    private RadioButton rbPDF;

    @FXML
    private ComboBox<?> cbFuncionario;

    @FXML
    private DatePicker dtDataInicial;

    @FXML
    private Label lblTitle;

    @FXML
    private DatePicker dtDataFinal;

    @FXML
    private ToggleGroup groupRadio;

    @FXML
    private Label lblStatus;

    @FXML
    private ComboBox<?> cbDiaSemana;

    @FXML
    private ComboBox<?> cbTipo;

    @FXML
    void generateReport(ActionEvent event) {

    }

    @FXML
    void back(ActionEvent event) {

    }
}

