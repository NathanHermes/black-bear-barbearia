package br.ifsp.edu.blackbearbarbearia.application.controller;

import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Role;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.*;

public class BookingMainController {
    @FXML
    private Button btnClientsManage;
    @FXML
    private Button btnEmployeesManage;
    @FXML
    private Button btnServiceManage;
    @FXML
    private Button btnLogout;
    @FXML
    private DatePicker dtData;
    @FXML
    private TextField inputHours;
    @FXML
    private ComboBox<?> cbFuncionario;
    @FXML
    private ComboBox<?> cbServico;
    @FXML
    private ComboBox<?> cbCliente;
    @FXML
    private Button btnFiltrar;
    @FXML
    private TableView<Booking> tbvBookings;
    @FXML
    private TableColumn<Booking, String> cClient;
    @FXML
    private TableColumn<Booking, String> cEmployee;
    @FXML
    private TableColumn<Booking, String> cService;
    @FXML
    private TableColumn<Booking, LocalDate> cDate;
    @FXML
    private TableColumn<Booking, String> cStatus;
    @FXML
    private TableColumn<Booking, String> cPaid;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnDesmarcar;
    @FXML
    private Button btnConcluir;
    @FXML
    private Button btnServicosPrestados;
    @FXML
    private Button btnNotaFiscal;
    @FXML
    private Button btnRelatorio;

    private ObservableList<Booking> bookingData;

    @FXML
    private void initialize() {
        userRoleVerify();
        setValueSourceToColumns();
        setItemListToTBV();
        loadBookingData();
    }

    private void loadBookingData() {
        try {
            var bookings = findBookingUseCase.findAll();
            bookingData.clear();
            bookingData.addAll(bookings);
        } catch (EntityNotFoundException exception) {
            bookingData.clear();
        }
    }

    private void setItemListToTBV() {
        bookingData = FXCollections.observableArrayList();
        tbvBookings.setItems(bookingData);
    }

    private void setValueSourceToColumns() {
        cClient.setCellValueFactory(new PropertyValueFactory<>("client"));
        cEmployee.setCellValueFactory(new PropertyValueFactory<>("employee"));
        cService.setCellValueFactory(new PropertyValueFactory<>("service"));
        cDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        cPaid.setCellValueFactory(new PropertyValueFactory<>("paid"));
    }

    private void userRoleVerify() {
        if (USER.getRole() != Role.ADMIN) {
            btnEmployeesManage.setDisable(true);
            btnServiceManage.setDisable(true);
        }
    }

    @FXML
    void goToManageClients(ActionEvent event) throws IOException {
        WindowLoader.setRoot("ClientMain");
    }

    @FXML
    void goToManageEmployees(ActionEvent event) throws IOException {
        WindowLoader.setRoot("EmployeeMain");
    }

    @FXML
    void goToManageService(ActionEvent event) {
        /*
         * Esperando terminar a implementação da tela de gerencimento de serviços
         */
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        WindowLoader.setRoot("Logout");
    }

    @FXML
    void filtrar(ActionEvent event) {
        /*
        * Esperando terminar a implementação do banco de dados
        */
    }

    @FXML
    void cadastrar(ActionEvent event) {
        /*
         * Esperando terminar a implementação da tela de cadastro de agendamento
         */
    }
    @FXML
    void concluir(ActionEvent event) {
        Booking bookingSelected = tbvBookings.getSelectionModel().getSelectedItem();
        BigDecimal employeeCommission = finishBookingUseCase.update(bookingSelected);
        loadBookingData();
    }
    @FXML
    void desmarcar(ActionEvent event) {
        
    }
    @FXML
    void relatorio(ActionEvent event) {

    }

    @FXML
    void notaFiscal(ActionEvent event) {

    }

    @FXML
    void servicosPrestados(ActionEvent event) {

    }



}
