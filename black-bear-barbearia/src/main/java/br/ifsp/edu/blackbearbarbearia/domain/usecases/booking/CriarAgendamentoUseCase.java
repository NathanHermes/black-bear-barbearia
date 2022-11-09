package br.ifsp.edu.blackbearbarbearia.domain.usecases.booking;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityAlreadyExistsException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

import java.time.LocalDate;

public class CriarAgendamentoUseCase {
    private final BookingDAO dao;
    public CriarAgendamentoUseCase(BookingDAO dao) {
        this.dao = dao;
    }

    public Integer create(Booking booking) {
        Validator<Booking> validator = new BookingInputRequestValidator();
        Notification notification = validator.validate(booking);

        if (notification.hasErros()) throw new IllegalArgumentException(notification.errorMessage());

        LocalDate date = booking.getDate();
        User user = booking.getUser();
        if (dao.findOneByDateAndUser(date, user).isPresent()) throw new EntityAlreadyExistsException("This date and user is already in use");

        // Verificar dispinibilidade do user

        return dao.create(booking);
    }
}

