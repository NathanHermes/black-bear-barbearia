package br.ifsp.edu.blackbearbarbearia.domain.usecases.booking;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.ServiceDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserInputRequestValidator;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

import java.time.LocalDate;
import java.util.List;

public class FiltrarFuncionariosAgendadosPorDiaUseCase {
    private final UserDAO userDAO;
    private final BookingDAO bookingDAO;
    private final ServiceDAO serviceDAO;

    public FiltrarFuncionariosAgendadosPorDiaUseCase(UserDAO userDAO, BookingDAO bookingDAO, ServiceDAO serviceDAO) {
        this.userDAO = userDAO;
        this.bookingDAO = bookingDAO;
        this.serviceDAO = serviceDAO;
    }

    public List<Booking> findByDay() {
        LocalDate date = LocalDate.now();
        if (bookingDAO.findAllByDate(date).isEmpty())
            throw new EntityNotFoundException("No booking found.");

        return bookingDAO.findAllByDate(date);
    }

    public List<Booking> findByUser(User user) {
        Validator<User> validator = new UserInputRequestValidator();
        Notification notification = validator.validate(user);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = user.getId();
        if (userDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("User not found.");

        return findByDay().stream()
                .filter(booking -> booking.getUser().equals(user))
                .toList();

    }

    public List<Booking> findByService(Service service) {
        Integer id = service.getId();
        if (serviceDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("Service not found.");

        return findByDay().stream()
                .filter(booking -> booking.getService().equals(service))
                .toList();
    }
}
