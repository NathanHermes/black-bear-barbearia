package br.ifsp.edu.blackbearbarbearia.application.controller;

import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.UserBuilder;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.ConverterSenhaParaMD5;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.USER;
import static br.ifsp.edu.blackbearbarbearia.application.main.Main.loginUseCase;

public class LoginController {
    @FXML
    private TextField loginInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Label lblError;

    public void makeLogin(ActionEvent actionEvent) throws IOException {
        lblError.setText("");
        var userBuilder = new UserBuilder();
        userBuilder.setLogin(loginInput.getText());
        userBuilder.setPasswordHash(ConverterSenhaParaMD5.converterSenhaParaMD5(passwordInput.getText()));

        try {
            USER = loginUseCase.login(userBuilder.getResult());
            WindowLoader.setRoot("BookingMain");
        } catch (IllegalArgumentException | EntityNotFoundException exception){
            lblError.setText(exception.getMessage());
        }
    }
}
