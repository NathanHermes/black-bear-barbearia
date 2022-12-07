package br.ifsp.edu.blackbearbarbearia.domain.usecases.booking;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

public class BookingInputRequestValidator extends Validator<Booking> {
    @Override
    public Notification validate(Booking booking) {
        Notification notification = new Notification();

        if (booking == null) {
            notification.addError("Booking is null");
            return notification;
        }

        if (booking.getDate() == null) notification.addError("Date is null");
        if (booking.getInfoClient() == null) notification.addError("Client is null");
        if (booking.getInfoService() == null) notification.addError("Service is null");
        if (booking.getInfoEmployee() == null) notification.addError("Employee is null");
        if (booking.getStatus() == null) notification.addError("Status is null");

        return notification;
    }
}
