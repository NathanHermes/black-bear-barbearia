package br.ifsp.edu.blackbearbarbearia.application.controller.booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ServiceHistoryController {

    @FXML
    private TableColumn<?, ?> cDate;

    @FXML
    private TableColumn<?, ?> cService;

    @FXML
    private Button btnFiltrar;

    @FXML
    private TableColumn<?, ?> cValue;

    @FXML
    private TableColumn<?, ?> cTime;

    @FXML
    private DatePicker dtDataInicial;

    @FXML
    private DatePicker dtDataFinal;

    @FXML
    private TableView<?> tbvService;

    @FXML
    private TableColumn<?, ?> cClient;

    @FXML
    void filtrar(ActionEvent event) {

    }

    @FXML
    void back(ActionEvent event) {

    }

}

