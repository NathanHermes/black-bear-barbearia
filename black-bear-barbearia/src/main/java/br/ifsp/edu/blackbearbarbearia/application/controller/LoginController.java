package br.ifsp.edu.blackbearbarbearia.application.controller;

import br.ifsp.edu.blackbearbarbearia.application.controller.employee.UpdatePasswordController;
import br.ifsp.edu.blackbearbarbearia.application.controller.popUp.PopUpController;
import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.UserBuilder;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.ConverterSenhaParaMD5;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
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

    public void makeLogin() throws IOException {
        var userBuilder = new UserBuilder();
        userBuilder.setLogin(loginInput.getText());
        userBuilder.setPasswordHash(passwordInput.getText());

        try {
            USER = loginUseCase.login(userBuilder.getResult());
            WindowLoader.setRoot("BookingMain");
        } catch (IllegalArgumentException | EntityNotFoundException exception){
            WindowLoader.setRoot("PopUp");
            PopUpController controller = (PopUpController) WindowLoader.getController();
            controller.setPopUp("error", exception.getMessage(), "Login");
        }
    }

    @FXML
    public void updatePassword() {
        try {
            WindowLoader.setRoot("UpdatePassword");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
