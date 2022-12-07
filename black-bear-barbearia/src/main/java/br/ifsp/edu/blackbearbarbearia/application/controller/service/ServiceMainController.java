package br.ifsp.edu.blackbearbarbearia.application.controller.service;

import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Type;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.findServiceUseCase;

public class ServiceMainController {
    @FXML
    private TableView<Service> tbvService;

    @FXML
    private TableColumn<Service, Integer> cID;

    @FXML
    private TableColumn<Service, String> cNome;

    @FXML
    private TableColumn<Service, BigDecimal> cPreco;

    @FXML
    private TableColumn<Service, BigDecimal>  cTaxa;

    @FXML
    private TableColumn<Service, BigDecimal> cComissao;
    @FXML
    private TableColumn<Service, Boolean> cAtivo;

    @FXML
    private TableColumn<Service, Type> cTipo;

    @FXML
    private TextField inputBusca;

    @FXML
    private Button btnEditar;

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
        cNome.setCellValueFactory(new PropertyValueFactory<>("name"));
        cPreco.setCellValueFactory(new PropertyValueFactory<>("price"));
        cComissao.setCellValueFactory(new PropertyValueFactory<>("comissionPercentage"));
        cTaxa.setCellValueFactory(new PropertyValueFactory<>("taxPercentage"));
        cAtivo.setCellValueFactory(new PropertyValueFactory<>("active"));
    }

    @FXML
    void search(ActionEvent event) {
        String name =  inputBusca.getText();

        Service service = findServiceUseCase.findKeyByName(name);

        serviceData.clear();
        serviceData.add(service);
    }

    @FXML
    void reloadTableView(ActionEvent event) {
        loadServiceData();
    }

    @FXML
    void save(ActionEvent event) {
        try {
            WindowLoader.setRoot("CreateOrUpdateService");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void update(ActionEvent event) {
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
    void back(ActionEvent event) {
        try {
            WindowLoader.setRoot("BookingMain");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


