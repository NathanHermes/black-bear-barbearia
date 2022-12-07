package br.ifsp.edu.blackbearbarbearia.application.controller.booking;

import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.INFOCOMMISSIONPOPUP;

public class EmployeeCommissionController {
    @FXML
    private Label name;
    @FXML
    private Label commission;

    public void setBooking(String employee, BigDecimal employeeCommission) {
        name.setText(employee);
        commission.setText(String.valueOf(employeeCommission.setScale(2, RoundingMode.HALF_EVEN)));
    }

    public void back() throws IOException {
        WindowLoader.setRoot("BookingMain");
    }
}
