package br.ifsp.edu.blackbearbarbearia.application.controller;

import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
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

import java.io.IOException;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.findClientUseCase;

public class ClientMainController {
    @FXML
    private TextField inputEmail;
    @FXML
    private TableView<Client> tbvClients;
    @FXML
    public TableColumn<Client, Integer> cID;
    @FXML
    private TableColumn<Client, String> cName;
    @FXML
    private TableColumn<Client, String> cEmail;
    @FXML
    private TableColumn<Client, String> cPhone;

    private ObservableList<Client> clientData;

    @FXML
    private void initialize() {
        setValueSourceToColumns();
        setItemListToTBV();
        loadBookingData();
    }

    private void loadBookingData() {
        try {
            var clients = findClientUseCase.findAll();
            clientData.clear();
            clientData.addAll(clients);
        } catch (EntityNotFoundException exception) {
            clientData.clear();
        }
    }

    private void setItemListToTBV() {
        clientData = FXCollections.observableArrayList();
        tbvClients.setItems(clientData);
    }

    private void setValueSourceToColumns() {
        cID.setCellValueFactory(new PropertyValueFactory<>("id"));
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    @FXML
    public void search(ActionEvent event) {
        String email = inputEmail.getText();

        Client client = findClientUseCase.findByEmail(email);

        clientData.clear();
        clientData.add(client);
    }

    @FXML
    public void reloadTableView(ActionEvent actionEvent) {
        loadBookingData();
    }

    @FXML
    public void save(ActionEvent event) {
        try {
            WindowLoader.setRoot("CreateOrUpdateClient");
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void update(ActionEvent event) {
        Client clientSelected = tbvClients.getSelectionModel().getSelectedItem();
        if (clientSelected != null) {
            try {
                WindowLoader.setRoot("CreateOrUpdateClient");
                CreateOrUpdateClientController controller = (CreateOrUpdateClientController) WindowLoader.getController();
                controller.setClient(clientSelected);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void back(ActionEvent actionEvent) {
        try {
            WindowLoader.setRoot("BookingMain");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}

