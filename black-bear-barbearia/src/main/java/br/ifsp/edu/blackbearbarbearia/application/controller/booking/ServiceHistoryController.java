package br.ifsp.edu.blackbearbarbearia.application.controller.booking;

import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.report.ListarHistoricoServicoPrestadosUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Date;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.*;

public class ServiceHistoryController {

    @FXML
    private TableColumn<Booking, String> cDate;

    @FXML
    private TableColumn<Booking, String> cService;

    @FXML
    private Button btnFiltrar;

    @FXML
    private TableColumn<Booking, String> cValue;

    @FXML
    private TableColumn<Booking, Time> cTime;

    @FXML
    private DatePicker dtDataInicial;

    @FXML
    private DatePicker dtDataFinal;

    @FXML
    private TableView<Booking> tbvService;

    @FXML
    private TableColumn<Booking, String> cClient;

    private ObservableList<Booking> bookingData;

    @FXML
    private void initialize() {
        setValueSourceToColumns();
        setItemListToTBV();
        loadBookingData();
    }

    private void loadBookingData() {
        try {
            var bookings = findBookingUseCase.findAll();

            bookings = bookings.stream()
                        .filter(booking -> booking.getInfoEmployee().getId() == USER.getId())
                        .toList();
            bookingData.clear();
            bookingData.addAll(bookings);
        } catch (EntityNotFoundException exception) {
            bookingData.clear();
        }
    }

    private void setItemListToTBV() {
        bookingData = FXCollections.observableArrayList();
        tbvService.setItems(bookingData);
    }

    private void setValueSourceToColumns() {
        cDate.setCellValueFactory((booking) -> new SimpleStringProperty(booking.getValue().getDate()));
        cService.setCellValueFactory((booking) -> new SimpleStringProperty(booking.getValue().getService()));
        cValue.setCellValueFactory((booking) -> new SimpleStringProperty(booking.getValue().getInfoService().getPrice().toString()));
        cTime.setCellValueFactory(new PropertyValueFactory<>("hour"));
        cClient.setCellValueFactory((booking) -> new SimpleStringProperty(booking.getValue().getClient()));
    }

    @FXML
    void filtrar(ActionEvent event) {
        Date dataInicial = Date.valueOf(dtDataInicial.getValue());
        Date dataFinal = Date.valueOf(dtDataFinal.getValue());

        if(dataInicial == null || dataFinal == null) {
            // Erro
            return;
        }

        listarHistoricoServicoPrestadosUseCase.create(USER, dataInicial, dataFinal);
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        WindowLoader.setRoot("BookingMain");
    }
}

