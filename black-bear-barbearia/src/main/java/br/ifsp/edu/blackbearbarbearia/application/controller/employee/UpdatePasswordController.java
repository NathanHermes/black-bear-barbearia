package br.ifsp.edu.blackbearbarbearia.application.controller.employee;

import br.ifsp.edu.blackbearbarbearia.application.controller.popUp.PopUpController;
import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.updatePasswordUseCase;

public class UpdatePasswordController {
    @FXML
    private TextField inputLogin;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private PasswordField inputConfirmPassword;

    @FXML
    public void update() throws IOException {
        String login = inputLogin.getText();
        String newPassword = inputPassword.getText();
        String confirmPassword = inputConfirmPassword.getText();

        try {
            Boolean response = updatePasswordUseCase.modifyPassword(login, newPassword, confirmPassword);
            WindowLoader.setRoot("PopUp");
            PopUpController controller = (PopUpController) WindowLoader.getController();
            if (response)
                controller.setPopUp("success", "A senha foi atualizada.", "Login");
            else
                controller.setPopUp("error", "Não foi possivel atualizar a senha.\nTente novamente.", "UpdatePassword");
        }catch (IllegalArgumentException | EntityNotFoundException e) {
            WindowLoader.setRoot("PopUp");
            PopUpController controller = (PopUpController) WindowLoader.getController();
            controller.setPopUp("error", e.getMessage(), "UpdatePassword");
        }
    }

    @FXML
    public void back() throws IOException {
        WindowLoader.setRoot("Login");
    }
}
