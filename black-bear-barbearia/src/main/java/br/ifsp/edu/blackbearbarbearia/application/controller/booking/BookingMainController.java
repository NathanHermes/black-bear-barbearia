package br.ifsp.edu.blackbearbarbearia.application.controller.booking;

import br.ifsp.edu.blackbearbarbearia.application.controller.popUp.PopUpController;
import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
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
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.*;

public class BookingMainController {
    @FXML
    private Button btnEmployeesManage;
    @FXML
    private Button btnServiceManage;
    @FXML
    private DatePicker dtData;
    @FXML
    private ComboBox<String> cbHorario;
    @FXML
    private ComboBox<String> cbFuncionario;
    @FXML
    private ComboBox<String> cbServico;
    @FXML
    private ComboBox<String> cbCliente;
    @FXML
    private TableView<Booking> tbvBookings;
    @FXML
    private TableColumn<Booking, Integer> cID;
    @FXML
    private TableColumn<Booking, String> cClient;
    @FXML
    private TableColumn<Booking, String> cEmployee;
    @FXML
    private TableColumn<Booking, String> cService;
    @FXML
    private TableColumn<Booking, String> cDate;
    @FXML
    private TableColumn<Booking, String> cHour;
    @FXML
    private TableColumn<Booking, String> cStatus;
    @FXML
    private TableColumn<Booking, String> cPaid;
    @FXML
    private Button btnReport;

    private ObservableList<Booking> bookingData;

    @FXML
    private void initialize() {
        userRoleVerify();
        setValueSourceToColumns();
        setItemListToTBV();
        loadBookingData();
        setItemsCbHorario();
        setItemsCbFuncionario();
        setItemsCbServico();
        setItemsCbCliente();
    }

    private void setItemsCbCliente() {
        var clients = findClientUseCase.findAll();
        var names = clients.stream()
                .map(Client::getName)
                .collect(Collectors.toList());

        cbCliente.setItems(FXCollections.observableList(names));
    }

    private void setItemsCbServico() {
        var services = findServiceUseCase.findAll();
        var names = services.stream()
                .map(Service::getName)
                .collect(Collectors.toList());

        cbServico.setItems(FXCollections.observableList(names));
    }

    private void setItemsCbFuncionario() {
        var employees = findEmployeeUseCase.findAll();
        var names = employees.stream()
                .map(User::getFullName)
                .collect(Collectors.toList());

        cbFuncionario.setItems(FXCollections.observableList(names));
    }

    private void setItemsCbHorario() {
        List<String> horarios = new ArrayList<>();
        horarios.add("08:00:00");
        horarios.add("08:30:00");
        horarios.add("09:00:00");
        horarios.add("09:30:00");
        horarios.add("10:00:00");
        horarios.add("10:30:00");
        horarios.add("11:00:00");
        horarios.add("11:30:00");
        horarios.add("12:00:00");
        horarios.add("12:30:00");
        horarios.add("13:00:00");
        horarios.add("13:30:00");
        horarios.add("14:00:00");
        horarios.add("14:30:00");
        horarios.add("15:00:00");
        horarios.add("15:30:00");
        horarios.add("16:00:00");
        horarios.add("16:30:00");
        horarios.add("17:00:00");
        horarios.add("17:30:00");
        horarios.add("18:00:00");
        horarios.add("18:30:00");
        horarios.add("19:00:00");
        horarios.add("19:30:00");
        cbHorario.setItems(FXCollections.observableList(horarios));
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
        cID.setCellValueFactory(new PropertyValueFactory<>("id"));
        cClient.setCellValueFactory(new PropertyValueFactory<>("client"));
        cEmployee.setCellValueFactory(new PropertyValueFactory<>("employee"));
        cService.setCellValueFactory(new PropertyValueFactory<>("service"));
        cDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        cHour.setCellValueFactory(new PropertyValueFactory<>("hour"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        cPaid.setCellValueFactory(new PropertyValueFactory<>("paid"));
    }

    private void userRoleVerify() {
        if (USER.getRole() != Role.ADMIN) {
            btnEmployeesManage.setDisable(true);
            btnServiceManage.setDisable(true);
            btnReport.setDisable(true);
        }
    }

    @FXML
    public void goToManageClients(ActionEvent event) throws IOException {
        WindowLoader.setRoot("ClientMain");
    }

    @FXML
    public void goToManageEmployees(ActionEvent event) throws IOException {
        WindowLoader.setRoot("EmployeeMain");
    }

    @FXML
    public void goToManageService(ActionEvent event) throws IOException {
        WindowLoader.setRoot("ServiceMain");
    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
        WindowLoader.setRoot("Logout");
    }

    @FXML
    public void filtrar(ActionEvent event) {
        loadBookingData();

        LocalDate date = dtData.getValue();
        String hour = cbHorario.getSelectionModel().getSelectedItem();
        String employee = cbFuncionario.getSelectionModel().getSelectedItem();
        String service = cbServico.getSelectionModel().getSelectedItem();
        String client = cbCliente.getSelectionModel().getSelectedItem();

        List<Booking> filteredBookings = bookingData;

        if(date != null) {
            filteredBookings = filteredBookings.stream()
                    .filter(booking -> booking.getNoFormattedDate().toString().equals(date.toString()))
                    .collect(Collectors.toList());
        }

        if(hour != null) {
            filteredBookings = filteredBookings.stream()
                    .filter(booking -> booking.getHour().toString().equals(hour))
                    .collect(Collectors.toList());
        }

        if(employee != null) {
            filteredBookings = filteredBookings.stream()
                    .filter(booking -> booking.getInfoEmployee().getFullName().equals(employee))
                    .collect(Collectors.toList());
        }

        if(service != null) {
            filteredBookings = filteredBookings.stream()
                    .filter(booking -> booking.getInfoService().getName().equals(employee))
                    .collect(Collectors.toList());
        }

        if(client != null) {
            filteredBookings = filteredBookings.stream()
                    .filter(booking -> booking.getInfoClient().getName().equals(client))
                    .collect(Collectors.toList());
        }

        bookingData.clear();
        bookingData.addAll(filteredBookings);
        clearInputs();
    }

    private void clearInputs() {
        dtData.setValue(null);
        cbHorario.getSelectionModel().select(null);
        cbFuncionario.getSelectionModel().select(null);
        cbServico.getSelectionModel().select(null);
        cbCliente.getSelectionModel().select(null);
    }

    @FXML
    public void goToCreateBooking() throws IOException {
        WindowLoader.setRoot("CreateBooking");
    }

    @FXML
    public void concludeBooking() throws IOException {
        Booking bookingSelected = tbvBookings.getSelectionModel().getSelectedItem();
        try {
            BigDecimal response = finishBookingUseCase.update(bookingSelected);
            WindowLoader.setRoot("EmployeeCommission");
            EmployeeCommissionController controller = (EmployeeCommissionController) WindowLoader.getController();
            controller.setBooking(bookingSelected.getEmployee(), response);
            generateNotaFiscalInPDFUseCase.generate(bookingSelected);
            loadBookingData();

        } catch (IllegalArgumentException | EntityNotFoundException | IllegalStateException e) {
            WindowLoader.setRoot("PopUp");
            PopUpController controller = (PopUpController) WindowLoader.getController();
            controller.setPopUp("error", e.getMessage(), "BookingMain");
        }
    }

    @FXML
    public void cancelBooking() throws IOException {
        Booking bookingSelected = tbvBookings.getSelectionModel().getSelectedItem();
        try {
            Boolean response = cancelBookingUseCase.update(bookingSelected);
            WindowLoader.setRoot("PopUp");
            PopUpController controller = (PopUpController) WindowLoader.getController();
            if (response) {
                controller.setPopUp("success", "Booking canceled", "BookingMain");
                loadBookingData();
            } else
                controller.setPopUp("error", "It was not possible to cancel the booking. Try again later.", "BookingMain");
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            WindowLoader.setRoot("PopUp");
            PopUpController controller = (PopUpController) WindowLoader.getController();
            controller.setPopUp("error", e.getMessage(), "BookingMain");
        }
    }

    @FXML
    public void historyOfBookingFromEmployee(ActionEvent event) {
        /*
        *  Esperando a criação da tela de histórico de serviços prestados
        * */
    }

    @FXML
    void relatorio(ActionEvent event) {

    }
}

