package br.ifsp.edu.blackbearbarbearia.domain.usecases.booking;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Status;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.DAO;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface BookingDAO extends DAO<Booking, Integer> {
    Boolean updateStatus(Integer bookingID, Integer statusID, String paid);
    List<Booking> findOneByDateAndUser(Date date, Integer userId);
    Optional<Booking> findOneByDate(Date date);
    List<Booking> findAllByDate(Date date);
    List<Booking> findAllByUserAndPeriod(User user, Date start, Date end);
    List<Booking> findAllByUser(User user);
    List<Booking> findAllByPeriod(Date start, Date end);
}
