package br.ifsp.edu.blackbearbarbearia.application.controller;

import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.INFOCOMMISSIONPOPUP;

public class EmployeeCommissionController {
    @FXML
    private Label name;
    @FXML
    private Label commission;

    @FXML
    private void initialize() {
        name.setText(INFOCOMMISSIONPOPUP.get(0));
        commission.setText(INFOCOMMISSIONPOPUP.get(1));
    }

    public void back(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("BookingMain");
    }
}
