package br.ifsp.edu.blackbearbarbearia.application.controller.client;

import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityAlreadyExistsException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import javafx.event.ActionEvent;
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
    private Label lblResponseMessage;
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
        btnSaveOrUpdate.setText("Cadastrar");
        inputName.setText("");
        inputPhone.setText("");
        inputEmail.setText("");
    }

    public void setClient(Client client) {
        if (client == null)
            throw new IllegalArgumentException("Client can not be null");

        this.client = client;
        setInfoClientIntoInputs();
    }

    private void setInfoClientIntoInputs() {
        lblTitle.setText("Editar");
        btnSaveOrUpdate.setText("Editar");
        inputName.setText(client.getName());
        inputPhone.setText(client.getPhone());
        inputEmail.setText(client.getEmail());
    }

    @FXML
    void saveOrUpdate(ActionEvent event) {
        lblResponseMessage.setText("");

        if (client == null) {
            getInfoClientFromInputs();
            try {
                Boolean response = createClientUseCase.create(client);
                if (response.equals(Boolean.TRUE))
                    lblResponseMessage.setText("Cliente cadastrado");
                else
                    lblResponseMessage.setText("Não foi possível cadastrar esse cliente.\nTente novamente mais tarde.");
            } catch (IllegalArgumentException | EntityAlreadyExistsException exception) {
                lblResponseMessage.setText(exception.getMessage());
            }

        } else {
            getInfoClientFromInputs();
            try {
                System.out.println(client);
                Boolean response = updateClientUseCase.update(client.getId(), client);
                if (response.equals(Boolean.TRUE))
                    lblResponseMessage.setText("Cliente atualizado");
                else
                    lblResponseMessage.setText("Não foi possível atualizar esse cliente.\nTente novamente mais tarde.");
            } catch (IllegalArgumentException | EntityNotFoundException exception) {
                lblResponseMessage.setText(exception.getMessage());
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

    public void back(ActionEvent actionEvent) {
        try {
            WindowLoader.setRoot("ClientMain");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

