package br.ifsp.edu.blackbearbarbearia.application.controller.popUp;

import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;

public class PopUpController {
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblMessage;

    private String titleType;
    private String popUpMessage;
    private String backParent;

    public void setPopUp(String titleType, String message, String parent) {
        this.titleType = titleType;
        this.popUpMessage = message;
        this.backParent = parent;
        setPopUpMessage();
    }

    private void setPopUpMessage() {
        if (titleType.equals("success")) {
            lblTitle.getStyleClass().add("title-success");
            lblTitle.setText("Sucesso");
        } else {
            lblTitle.getStyleClass().add("title-error");
            lblTitle.setText("Erro");
        }
        lblMessage.setText(popUpMessage);
    }

    @FXML
    public void back() {
        try {
            WindowLoader.setRoot(backParent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
