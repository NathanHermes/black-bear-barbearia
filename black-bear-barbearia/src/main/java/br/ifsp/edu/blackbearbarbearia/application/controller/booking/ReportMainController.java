package br.ifsp.edu.blackbearbarbearia.application.controller.booking;

import br.ifsp.edu.blackbearbarbearia.application.controller.popUp.PopUpController;
import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.Collectors;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.*;

public class ReportMainController {
    @FXML
    private TableColumn<Booking, String> cClient;

    @FXML
    private TableColumn<Booking, String> cDate;

    @FXML
    private TableColumn<Booking, String> cEmployee;

    @FXML
    private TableColumn<Booking, String> cService;

    @FXML
    private TableColumn<Booking, String> cTime;

    @FXML
    private TableColumn<Booking, String> cValue;


    @FXML
    private ComboBox<String> cbFuncionario;

    @FXML
    private ComboBox<String> cbServico;


    @FXML
    private DatePicker dtDataFinal;

    @FXML
    private DatePicker dtDataInicial;

    @FXML
    private RadioButton rbCSV;

    @FXML
    private RadioButton rbPDF;

    @FXML
    private TableView<Booking> tbvBookings;

    private ObservableList<Booking> bookingData;


    @FXML
    void initialize() {
        setValueSourceToColumns();
        setItemListToTBV();
        loadBookingData();
        setItemsCbServico();
        setItemsCbEmployee();

        rbCSV.setDisable(true);
        rbPDF.setDisable(true);
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
        cClient.setCellValueFactory((booking) -> new SimpleStringProperty(booking.getValue().getClient()));
        cDate.setCellValueFactory((booking) -> new SimpleStringProperty(booking.getValue().getDate()));;
        cEmployee.setCellValueFactory((booking) -> new SimpleStringProperty(booking.getValue().getEmployee()));;
        cService.setCellValueFactory((booking) -> new SimpleStringProperty(booking.getValue().getService()));;
        cTime.setCellValueFactory(new PropertyValueFactory<>("hour"));
        cValue.setCellValueFactory((booking) -> new SimpleStringProperty(booking.getValue().getInfoService().getPrice().toString()));
    }

    private void setItemsCbEmployee() {
        var employees = findEmployeeUseCase.findAll();
        var names = employees.stream()
                .map(User::getFullName)
                .collect(Collectors.toList());

        cbFuncionario.setItems(FXCollections.observableList(names));
    }

    private void setItemsCbServico() {
        var services = findServiceUseCase.findAll();
        var names = services.stream()
                .map(Service::getName)
                .collect(Collectors.toList());

        cbServico.setItems(FXCollections.observableList(names));
    }

    @FXML
    void filtrar() {
        String funcionario = cbFuncionario.getSelectionModel().getSelectedItem();
        String servico = cbServico.getSelectionModel().getSelectedItem();
        LocalDate dataInicial = dtDataInicial.getValue();
        LocalDate dataFinal = dtDataFinal.getValue();

        var bookingsFiltred = findBookingUseCase.findAll();
        if (dataInicial != null && dataFinal != null)
            bookingsFiltred= gerarRelatorioSolicitadoUseCase.findByPeriod(Date.valueOf(dataInicial), Date.valueOf(dataFinal));

        if (funcionario != null) {
            bookingsFiltred = bookingsFiltred.stream()
                    .filter(booking -> booking.getEmployee().equals(funcionario))
                    .collect(Collectors.toList());
        }

        if (servico != null) {
            bookingsFiltred = bookingsFiltred.stream()
                    .filter(booking -> booking.getService().equals(servico))
                    .collect(Collectors.toList());
        }

        bookingData.clear();
        bookingData.addAll(bookingsFiltred);
    }

    @FXML
    void generateReport() throws IOException {
        try {
            generateReportInPDF.generate(bookingData);
            WindowLoader.setRoot("PopUp");
            PopUpController controller = (PopUpController) WindowLoader.getController();
            controller.setPopUp("success", "Report created", "ReportMain");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void back() throws IOException {
        WindowLoader.setRoot("BookingMain");
    }
}
