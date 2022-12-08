package br.ifsp.edu.blackbearbarbearia.domain.usecases.report;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.booking.BookingDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserInputRequestValidator;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class ListarHistoricoServicoPrestadosUseCase {
    private final BookingDAO bookingDAO;
    private final UserDAO userDAO;

    public ListarHistoricoServicoPrestadosUseCase(BookingDAO bookingDAO, UserDAO userDAO) {
        this.bookingDAO = bookingDAO;
        this.userDAO = userDAO;
    }

    public List<Booking> create(User user, Date start, Date end) {
        Validator<User> validator = new UserInputRequestValidator();
        Notification notification = validator.validate(user);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        if (start.after(Date.valueOf(LocalDate.now())) || end.after(Date.valueOf(LocalDate.now())) || start.after(end))
            throw new IllegalArgumentException("Period is invalid.");

        Integer id = user.getId();
        if (userDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("User not found.");

//        if (bookingDAO.findAllByUser(user).isEmpty())
//            throw new EntityNotFoundException("No booking found.");

        return bookingDAO.findAllByUserAndPeriod(user, start, end);
    }
}
