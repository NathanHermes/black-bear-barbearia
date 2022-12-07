package br.ifsp.edu.blackbearbarbearia.application.controller.client;

import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
            clientData.clear();
            clientData.addAll(findClientUseCase.findAll());
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
    public void search() {
        String email = inputEmail.getText();

        Client client = findClientUseCase.findByEmail(email);

        clientData.clear();
        clientData.add(client);
    }

    @FXML
    public void reloadTableView() {
        loadBookingData();
    }

    @FXML
    public void save() {
        try {
            WindowLoader.setRoot("CreateOrUpdateClient");
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void update() {
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
    public void back() {
        try {
            WindowLoader.setRoot("BookingMain");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}

