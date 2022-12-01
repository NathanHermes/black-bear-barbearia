package br.ifsp.edu.blackbearbarbearia.application.controller;

import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.UserBuilder;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.USER;
import static br.ifsp.edu.blackbearbarbearia.application.main.Main.loginUseCase;

public class LoginController {
    @FXML
    private Label lblError;
    @FXML
    private TextField txtLogin;
    @FXML
    private TextField txtPassword;

    public void makeLogin(ActionEvent actionEvent) {
        var userBuilder = new UserBuilder();
        userBuilder.setLogin(txtLogin.getText());
        userBuilder.setPasswordHash(txtPassword.getText());

        try {
            USER = loginUseCase.login(userBuilder.getResult());
            WindowLoader.setRoot("HomeAgendamento");
        } catch (IllegalArgumentException | EntityNotFoundException exception){
            lblError.setText(exception.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
