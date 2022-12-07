package br.ifsp.edu.blackbearbarbearia.domain.usecases.report;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.booking.BookingDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.ServiceDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserInputRequestValidator;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

import java.sql.Date;
import java.util.List;

public class GerarRelatorioSolicitadoUseCase {
    private final BookingDAO bookingDAO;
    private final UserDAO userDAO;
    private final ServiceDAO serviceDAO;

//    private Date start;
//    private Date end;

    public GerarRelatorioSolicitadoUseCase(BookingDAO bookingDAO, UserDAO userDAO, ServiceDAO serviceDAO) {
        this.bookingDAO = bookingDAO;
        this.userDAO = userDAO;
        this.serviceDAO = serviceDAO;

//        this.start = start;
//        this.end = end;
    }

    public List<Booking> findByPeriod(Date start, Date end) {
        if (bookingDAO.findAllByPeriod(start, end).isEmpty())
            throw new EntityNotFoundException("No booking found.");

        return bookingDAO.findAllByPeriod(start, end);
    }

    public List<Booking> findByUser(User user) {
        Validator<User> validator = new UserInputRequestValidator();
        Notification notification = validator.validate(user);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = user.getId();
        if (userDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("User not found.");

        return findByPeriod(start, end).stream()
                .filter(booking -> booking.getEmployee().equals(user))
                .toList();
    }

    public List<Booking> findByService(Service service) {
        Integer id = service.getId();
        if (serviceDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("Service not found.");

        return findByPeriod(start, end).stream()
                .filter(booking -> booking.getService().equals(service))
                .toList();

    }
}
