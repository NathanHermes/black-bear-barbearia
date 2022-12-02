package br.ifsp.edu.blackbearbarbearia.domain.usecases.booking;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityAlreadyExistsException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

import java.time.LocalDate;

public class CreateBookingUseCase {
    private final BookingDAO dao;
    public CreateBookingUseCase(BookingDAO dao) {
        this.dao = dao;
    }

    public Integer create(Booking booking) {
        Validator<Booking> validator = new BookingInputRequestValidator();
        Notification notification = validator.validate(booking);

        if (notification.hasErros()) throw new IllegalArgumentException(notification.errorMessage());

        LocalDate date = booking.getNoFormattedDate();
        User user = booking.getInfoEmployee();
        if (dao.findOneByDateAndUser(date, user).isPresent()) throw new EntityAlreadyExistsException("This date and user is already in use");

        return dao.create(booking);
    }
}

