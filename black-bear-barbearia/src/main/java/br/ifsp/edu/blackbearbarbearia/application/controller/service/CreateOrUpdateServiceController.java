package br.ifsp.edu.blackbearbarbearia.application.controller.service;

import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Type;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.createServiceUseCase;

public class CreateOrUpdateServiceController {
    @FXML
    private RadioButton rbNao;

    @FXML
    private RadioButton rbSim;

    @FXML
    private TextField inputComissao;

    @FXML
    private TextField inputPreco;

    @FXML
    private CheckBox cbHair;

    @FXML
    private CheckBox cbBeard;

    @FXML
    private CheckBox cbOther;

    @FXML
    private Button btnSaveOrUpdate;

    @FXML
    private Label lblTitle;

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputTaxa;

    private Service service;

    @FXML
    private void initialize() {
        if (service == null)
            clearInputs();

    }

    private void clearInputs() {
        lblTitle.setText("Cadastrar");
        btnSaveOrUpdate.setText("Cadastrar");
        inputName.setText("");
        inputPreco.setText("");
        inputComissao.setText("");
        inputTaxa.setText("");
    }

    public void setService(Service service) {
        if (service == null)
            throw new IllegalArgumentException("Service can not be null");

        this.service = service;
        setInfoServiceIntoInputs();
        inputName.setDisable(true);
        inputPreco.setDisable(true);

    }

    private void setInfoServiceIntoInputs() {
        lblTitle.setText("Editar");
        btnSaveOrUpdate.setText("Editar");
        inputName.setText(service.getName());
        inputPreco.setText(String.valueOf(service.getPrice()));
        inputComissao.setText(String.valueOf(service.getComissionPercentage()));
        inputTaxa.setText(String.valueOf(service.getTaxPercentage()));

    }

    @FXML
    void saveOrUpdate(ActionEvent event) {
        if (service == null) {
            getInfoServiceFromInputs();
            try {
                Boolean response = createServiceUseCase.create(service);
            } finally {

            }
        }
    }

    private void getInfoServiceFromInputs() {
        //if (service == null)
         //   service = new Service();

        service.setName(inputName.getText());
        service.setPrice(new BigDecimal(inputPreco.getText()));
        service.setComissionPercentage(new BigDecimal(inputComissao.getText()));
        service.setTaxPercentage(new BigDecimal(inputTaxa.getText()));

    }

    @FXML
    void back(ActionEvent event) {
        try {
            WindowLoader.setRoot("ServiceMain");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


