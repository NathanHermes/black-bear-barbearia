package br.ifsp.edu.blackbearbarbearia.application.controller.booking;

import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Type;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private ComboBox<String> cbDiaSemana;

    @FXML
    private ComboBox<String> cbFuncionario;

    @FXML
    private ComboBox<String> cbServico;

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private DatePicker dtDataFinal;

    @FXML
    private DatePicker dtDataInicial;

    @FXML
    private ToggleGroup groupRadio;

    @FXML
    private Label lblStatus;

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
        setItemsCbFuncionario();
        setItemsCbDiaSemana();
        setItemsCbTipo();
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

    private void setItemsCbTipo() {
        var types = Type.values();
        var names = new ArrayList<String>();

        for (Type type : types) {
            names.add(type.name());
        }

        cbTipo.setItems(FXCollections.observableList(names));
    }

    private void setItemsCbDiaSemana() {
        var days = DayOfWeek.values();
        var names = new ArrayList<String>();

        for (DayOfWeek day : days) {
            names.add(day.name());
        }

        cbDiaSemana.setItems(FXCollections.observableList(names));
    }

    private void setItemsCbFuncionario() {
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
    void filtrar(ActionEvent event) {
        String diaSemana = cbDiaSemana.getSelectionModel().getSelectedItem();
        String funcionario = cbFuncionario.getSelectionModel().getSelectedItem();
        String servico = cbServico.getSelectionModel().getSelectedItem();
        String tipo = cbTipo.getSelectionModel().getSelectedItem();
        LocalDate dataFinal = dtDataFinal.getValue();
        LocalDate dataInicial = dtDataFinal.getValue();

        var bookingsFiltred = findBookingUseCase.findAll();

//        if (diaSemana != null) {
//
//        }

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

//        if (tipo != null) {
//
//        }

        if (dataInicial != null && dataFinal != null) {
            bookingsFiltred = bookingsFiltred.stream()
                    .filter(booking -> booking.getNoFormattedDate().toLocalDate().isAfter(dataInicial))
                    .filter(booking -> booking.getNoFormattedDate().toLocalDate().isBefore(dataFinal))
                    .collect(Collectors.toList());
        }

        bookingData.clear();
        bookingData.addAll(bookingsFiltred);
    }

    @FXML
    void generateReport(ActionEvent event) {
        generateReportInPDF.generate(bookingData);
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        WindowLoader.setRoot("BookingMain");
    }
}
