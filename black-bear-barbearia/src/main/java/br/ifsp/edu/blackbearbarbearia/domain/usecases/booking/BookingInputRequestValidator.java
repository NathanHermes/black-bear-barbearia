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
        if (booking.isPaid() == null) notification.addError("Paid is null");
        if (booking.getClient() == null) notification.addError("Client is null");
        if (booking.getService() == null) notification.addError("Service is null");
        if (booking.getUser() == null) notification.addError("User is null");
        if (booking.getStatus() == null) notification.addError("Status is null");

        return notification;
    }
}
