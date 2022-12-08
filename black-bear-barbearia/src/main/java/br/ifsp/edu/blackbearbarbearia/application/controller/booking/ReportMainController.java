package br.ifsp.edu.blackbearbarbearia.application.controller.booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;

public class ReportMainController {

    @FXML
    private RadioButton rbCSV;

    @FXML
    private TableColumn<?, ?> cService;

    @FXML
    private TableColumn<?, ?> cDate;

    @FXML
    private TableColumn<?, ?> cEmployee;

    @FXML
    private ComboBox<?> cbServico;

    @FXML
    private ComboBox<?> cbFuncionario;

    @FXML
    private TableColumn<?, ?> cTime;

    @FXML
    private TableView<?> tbvBookings;

    @FXML
    private ToggleGroup groupRadio;

    @FXML
    private Label lblStatus;

    @FXML
    private TableColumn<?, ?> cComission;

    @FXML
    private ComboBox<?> cbDiaSemana;

    @FXML
    private TableColumn<?, ?> cClient;

    @FXML
    private ComboBox<?> cbTipo;

    @FXML
    private RadioButton rbPDF;

    @FXML
    private TableColumn<?, ?> cValue;

    @FXML
    private DatePicker dtDataInicial;

    @FXML
    private DatePicker dtDataFinal;

    @FXML
    private TableColumn<?, ?> cQtdd;

    @FXML
    void filtrar(ActionEvent event) {

    }

    @FXML
    void generateReport(ActionEvent event) {

    }

    @FXML
    void back(ActionEvent event) {

    }
}
