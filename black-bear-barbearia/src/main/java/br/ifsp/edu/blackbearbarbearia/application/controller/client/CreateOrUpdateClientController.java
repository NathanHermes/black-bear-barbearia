package br.ifsp.edu.blackbearbarbearia.application.controller.client;

import br.ifsp.edu.blackbearbarbearia.application.controller.popUp.PopUpController;
import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityAlreadyExistsException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.createClientUseCase;
import static br.ifsp.edu.blackbearbarbearia.application.main.Main.updateClientUseCase;

public class CreateOrUpdateClientController {
    @FXML
    private Label lblTitle;
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputPhone;
    @FXML
    private TextField inputEmail;
    @FXML
    private Button btnSaveOrUpdate;

    private Client client;

    @FXML
    private void initialize() {
        if (client == null)
            clearInputs();
    }

    private void clearInputs() {
        lblTitle.setText("Cadastrar");
        btnSaveOrUpdate.setText("C A D A S T R A R");
        inputName.setText("");
        inputPhone.setText("");
        inputEmail.setText("");
    }

    public void setClient(Client clientSelected) {
        if (clientSelected == null)
            throw new IllegalArgumentException("Client can not be null");

        this.client = clientSelected;
        setInfoClientIntoInputs();
    }

    private void setInfoClientIntoInputs() {
        lblTitle.setText("Editar");
        btnSaveOrUpdate.setText("E D I T A R");
        inputName.setText(client.getName());
        inputPhone.setText(client.getPhone());
        inputEmail.setText(client.getEmail());
    }

    @FXML
    void saveOrUpdate() throws IOException {
        WindowLoader.setRoot("PopUp");
        PopUpController controller = (PopUpController) WindowLoader.getController();
        if (client == null) {
            getInfoClientFromInputs();
            try {
                Boolean response = createClientUseCase.create(client);

                if (response)
                    controller.setPopUp("success", "Client created", "ClientMain");
                else
                    controller.setPopUp("error", "Unable to create client.\nTry again.", "ClientMain");
            } catch (IllegalArgumentException | EntityAlreadyExistsException e) {
                controller.setPopUp("error", e.getMessage(), "CreateOrUpdateClient");
            }
        } else {
            getInfoClientFromInputs();
            try {
                Boolean response = updateClientUseCase.update(client.getId(), client);
                if (response)
                    controller.setPopUp("success", "Client updated", "ClientMain");
                else
                    controller.setPopUp("error", "Unable to update client.\nTry again.", "ClientMain");
            } catch (IllegalArgumentException | EntityNotFoundException e) {
                controller.setPopUp("error", e.getMessage(), "CreateOrUpdateClient");
            }
        }
        client = null;
    }

    private void getInfoClientFromInputs() {
        if (client == null)
            client = new Client();

        client.setName(inputName.getText());
        client.setPhone(inputPhone.getText());
        client.setEmail(inputEmail.getText());
    }

    public void back() throws IOException {
        WindowLoader.setRoot("ClientMain");
    }
}

