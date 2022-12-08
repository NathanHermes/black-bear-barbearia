package br.ifsp.edu.blackbearbarbearia.application.controller.service;

import br.ifsp.edu.blackbearbarbearia.application.controller.popUp.PopUpController;
import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.ServiceBuilder;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Type;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityAlreadyExistsException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.createServiceUseCase;
import static br.ifsp.edu.blackbearbarbearia.application.main.Main.updateServiceUseCase;

public class CreateOrUpdateServiceController {
    @FXML
    private Label lblTitle;
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputPrince;
    @FXML
    private TextField inputCommission;
    @FXML
    private TextField inputTaxa;
    @FXML
    private RadioButton rbNo;

    @FXML
    private RadioButton rbYes;

    @FXML
    private CheckBox cbHair;

    @FXML
    private CheckBox cbBeard;

    @FXML
    private CheckBox cbOther;

    @FXML
    private Button btnSaveOrUpdate;

    private Service service;

    @FXML
    private void initialize() {
        if (service == null)
            rbNo.setDisable(true);
            rbYes.setDisable(true);
            clearInputs();
    }

    private void clearInputs() {
        lblTitle.setText("Save");
        btnSaveOrUpdate.setText("S A V E");
        inputName.setText("");
        inputPrince.setText("");
        inputCommission.setText("");
        inputTaxa.setText("");
        rbNo.setSelected(false);
        rbYes.setSelected(false);
        cbHair.setSelected(false);
        cbBeard.setSelected(false);
        cbOther.setSelected(false);
    }

    public void setService(Service service) {
        if (service == null)
            throw new IllegalArgumentException("Service can not be null");

        this.service = service;
        setInfoServiceIntoInputs();
        inputName.setDisable(true);
        rbNo.setDisable(false);
        rbYes.setDisable(false);
    }

    private void setInfoServiceIntoInputs() {
        lblTitle.setText("Editar");
        btnSaveOrUpdate.setText("Editar");
        inputName.setText(service.getName());
        inputPrince.setText(String.valueOf(service.getPrice()));
        inputCommission.setText(String.valueOf(service.getComissionPercentage()));
        inputTaxa.setText(String.valueOf(service.getTaxPercentage()));
        // Verifica se está ativo ou não
        if (service.getActive())
            rbYes.setSelected(true);
        else
            rbNo.setSelected(true);

        // Verifica os tipos do serviço
        List<Type> types = service.getTypes();

        if (types.contains(Type.HAIR))
            cbHair.setSelected(true);
        if (types.contains(Type.BEARD))
            cbBeard.setSelected(true);
        if (types.contains(Type.OTHER))
            cbOther.setSelected(true);
    }

    @FXML
    void saveOrUpdate(ActionEvent event) throws IOException {
        WindowLoader.setRoot("PopUp");
        PopUpController controller = (PopUpController) WindowLoader.getController();

        if (service == null) {
            getInfoServiceFromInputs();

            try {
                Boolean response = createServiceUseCase.create(service);
                if (response)
                    controller.setPopUp("success", "Service created", "ServiceMain");
                else
                    controller.setPopUp("error", "Unable to create service.\nTry again.", "ServiceMain");
            } catch (IllegalArgumentException | EntityAlreadyExistsException e) {
                controller.setPopUp("error", e.getMessage(), "CreateOrUpdateService");
            }
        } else {
            getInfoServiceFromInputs();

            try {
                Boolean response = updateServiceUseCase.update(service.getId(), service);
                if (response)
                    controller.setPopUp("success", "Service updated", "ServiceMain");
                else
                    controller.setPopUp("error", "Unable to update service.\nTry again.", "ServiceMain");
            } catch (IllegalArgumentException | EntityNotFoundException e) {
                controller.setPopUp("error", e.getMessage(), "CreateOrUpdateService");
            }
        }
        service = null;
    }

    private void getInfoServiceFromInputs() {
        ServiceBuilder serviceBuilder = new ServiceBuilder();
        serviceBuilder.setPrice(new BigDecimal(inputPrince.getText()));
        serviceBuilder.setComissionPercentage(new BigDecimal(inputCommission.getText()));
        serviceBuilder.setTaxPercentage(new BigDecimal(inputTaxa.getText()));

        // Verifica os checkbox selectionados
        List<Type> types = new ArrayList<>();

        if (cbHair.isSelected())
            types.add(Type.valueOf(cbHair.getText()));
        if (cbBeard.isSelected())
            types.add(Type.valueOf(cbBeard.getText()));
        if (cbOther.isSelected())
            types.add(Type.valueOf(cbOther.getText()));

        serviceBuilder.setTypes(types);

        if (service != null) {
            serviceBuilder.setId(service.getId());

            // Verifica qual opção do radioButton está selecionada
            if (rbYes.isSelected()) {
                serviceBuilder.setActive(true);
            } else {
                serviceBuilder.setActive(false);
            }
        } else {
            serviceBuilder.setName(inputName.getText());
        }
        service = serviceBuilder.getResult();
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


