package br.ifsp.edu.blackbearbarbearia.domain.usecases.booking;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.DAO;

import java.time.LocalDate;
import java.util.Optional;

public interface BookingDAO extends DAO<Booking, Integer> {
    Optional<Booking> findOneByDateAndUser(LocalDate date, User user);
}
