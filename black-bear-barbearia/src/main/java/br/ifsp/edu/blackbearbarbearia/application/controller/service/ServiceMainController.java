package br.ifsp.edu.blackbearbarbearia.application.controller.service;

import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.math.BigDecimal;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.findServiceUseCase;

public class ServiceMainController {
    @FXML
    private TextField inputNameService;
    @FXML
    private TableView<Service> tbvService;

    @FXML
    private TableColumn<Service, Integer> cID;

    @FXML
    private TableColumn<Service, String> cName;

    @FXML
    private TableColumn<Service, BigDecimal> cPrince;

    @FXML
    private TableColumn<Service, BigDecimal>  cTaxa;

    @FXML
    private TableColumn<Service, BigDecimal> cCommission;
    @FXML
    private TableColumn<Service, Boolean> cActive;

    private ObservableList<Service> serviceData;

    @FXML
    private void initialize() {
        setValueSourceToColumns();
        setItemListToTBV();
        loadServiceData();
    }

    private void loadServiceData() {
        try {
            serviceData.clear();
            serviceData.addAll(findServiceUseCase.findAll());
        } catch (EntityNotFoundException exception) {
            serviceData.clear();
        }
    }

    private void setItemListToTBV() {
        serviceData = FXCollections.observableArrayList();
        tbvService.setItems(serviceData);
    }

    private void setValueSourceToColumns() {
        cID.setCellValueFactory(new PropertyValueFactory<>("id"));
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cPrince.setCellValueFactory(new PropertyValueFactory<>("price"));
        cCommission.setCellValueFactory(new PropertyValueFactory<>("comissionPercentage"));
        cTaxa.setCellValueFactory(new PropertyValueFactory<>("taxPercentage"));
        cActive.setCellValueFactory(new PropertyValueFactory<>("active"));
    }

    @FXML
    void search() {
        String name =  inputNameService.getText();

        if (name == null || name.isBlank())
            loadServiceData();
        else {
            Service service = findServiceUseCase.findKeyByName(name);
            serviceData.clear();
            serviceData.add(service);
        }
    }

    @FXML
    void reloadTableView() {
        loadServiceData();
    }

    @FXML
    void save() throws IOException {
       WindowLoader.setRoot("CreateOrUpdateService");
    }

    @FXML
    void update() {
        Service serviceSelected = tbvService.getSelectionModel().getSelectedItem();
        if (serviceSelected != null) {
            try {
                WindowLoader.setRoot("CreateOrUpdateService");
                CreateOrUpdateServiceController controller = (CreateOrUpdateServiceController) WindowLoader.getController();
                controller.setService(serviceSelected);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void back() throws IOException {
        WindowLoader.setRoot("BookingMain");
    }
}


