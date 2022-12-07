package br.ifsp.edu.blackbearbarbearia.domain.usecases.booking;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.client.ClientDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.ServiceDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityAlreadyExistsException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;

import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static br.ifsp.edu.blackbearbarbearia.application.main.Main.findEmployeeUseCase;

public class CreateBookingUseCase {
    private final BookingDAO bookingDAO;
    private final UserDAO userDAO;
    private final ServiceDAO serviceDAO;
    private final ClientDAO clientDAO;
    public CreateBookingUseCase(BookingDAO bookingDAO, UserDAO userDAO, ServiceDAO serviceDAO, ClientDAO clientDAO) {
        this.bookingDAO = bookingDAO;
        this.userDAO = userDAO;
        this.serviceDAO = serviceDAO;
        this.clientDAO = clientDAO;
    }

    public Boolean create(Date date, Time hour, String client, String service, String employee) {
        if (date == null)
            throw new IllegalArgumentException("Date is null or empty");

        LocalDate now = LocalDate.now();
        if (!date.equals(Date.valueOf(now)) && date.before(Date.valueOf(now)))
            throw new IllegalArgumentException("Date is invalid");

        if (userDAO.findIDByFullName(employee).isEmpty())
            throw new EntityNotFoundException("Employee not found");
        Integer userId = userDAO.findIDByFullName(employee).get();

        List<Booking> bookings = bookingDAO.findOneByDateAndUser(date, userId);
        long count = bookings.stream().filter(booking -> booking.getNoFormattedHour().equals(hour)).count();
        if (count > 0)
            throw new IllegalArgumentException("Hour is already booking");

        User daoEmployee = null;
        if (userDAO.findOne(userId).isPresent())
            daoEmployee = userDAO.findOne(userId).get();

        if (serviceDAO.findOneByName(service).isEmpty())
            throw new EntityNotFoundException("Service not found");
        Service daoService = serviceDAO.findOneByName(service).get();

        if (clientDAO.findOneByName(client).isEmpty())
            throw new EntityNotFoundException("Client not found");
        Client daoClient = clientDAO.findOneByName(client).get();

        Booking newBooking = new Booking(date, hour, daoClient, daoService, daoEmployee);
        return bookingDAO.create(newBooking);
    }
}

