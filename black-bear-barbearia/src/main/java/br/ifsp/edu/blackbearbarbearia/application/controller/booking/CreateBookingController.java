package br.ifsp.edu.blackbearbarbearia.application.controller.booking;

import br.ifsp.edu.blackbearbarbearia.application.controller.popUp.PopUpController;
import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.*;

public class CreateBookingController {
    @FXML
    private ComboBox<String> cbService;
    @FXML
    private DatePicker dtDate;
    @FXML
    private ComboBox<String> cbEmployee;
    @FXML
    private ComboBox<String> cbHour;
    @FXML
    private ComboBox<String> cbClient;

    @FXML
    private void initialize() {
        setItemsCbFuncionario();
        setItemsCbServico();
        setItemsCbCliente();
        setItemsCbHorario();
    }

    private void setItemsCbCliente() {
        var clients = findClientUseCase.findAll();
        var names = clients.stream()
                .map(Client::getName)
                .collect(Collectors.toList());

        cbClient.setItems(FXCollections.observableList(names));
    }

    private void setItemsCbServico() {
        var services = findServiceUseCase.findAll();
        var names = services.stream()
                .map(Service::getName)
                .collect(Collectors.toList());

        cbService.setItems(FXCollections.observableList(names));
    }

    private void setItemsCbFuncionario() {
        var employees = findEmployeeUseCase.findAll();
        var names = employees.stream()
                .map(User::getFullName)
                .collect(Collectors.toList());

        cbEmployee.setItems(FXCollections.observableList(names));
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
        cbHour.setItems(FXCollections.observableList(horarios));
    }

    @FXML
    public void save() {
        String date = dtDate.getValue().toString();
        String hour = cbHour.getValue();
        String client = cbClient.getValue();
        String service = cbService.getValue();
        String employee = cbEmployee.getValue();

        try {
            Boolean response = createBookingUseCase.create(Date.valueOf(date), Time.valueOf(hour), client, service, employee);
            WindowLoader.setRoot("PopUp");
            PopUpController controller = (PopUpController) WindowLoader.getController();
            if (response)
                controller.setPopUp("success", "Booking created", "BookingMain");
             else
                controller.setPopUp("error", "Unable to create booking.\nTry again.", "CreateBooking");
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            try {
                WindowLoader.setRoot("PopUp");
                PopUpController controller = (PopUpController) WindowLoader.getController();
                controller.setPopUp("error", e.getMessage(), "CreateBooking");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void back() {
        try {
            WindowLoader.setRoot("BookingMain");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}


