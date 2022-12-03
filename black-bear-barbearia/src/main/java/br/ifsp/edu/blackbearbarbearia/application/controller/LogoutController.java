package br.ifsp.edu.blackbearbarbearia.application.controller;

import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.USER;

public class LogoutController {
    @FXML
    public void goBack(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("BookingMain");
    }

    @FXML
    public void logout(ActionEvent actionEvent) throws IOException {
        USER = null;
        WindowLoader.setRoot("Login");
    }
}
