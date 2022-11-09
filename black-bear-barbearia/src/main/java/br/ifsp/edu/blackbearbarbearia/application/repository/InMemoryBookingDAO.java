package br.ifsp.edu.blackbearbarbearia.application.repository;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.booking.BookingDAO;

import java.time.LocalDate;
import java.util.*;

public class InMemoryBookingDAO implements BookingDAO {
    private static final Map<Integer, Booking> database = new LinkedHashMap<>();
    private static Integer bookingID;

    @Override
    public Integer create(Booking booking) {
        bookingID++;
        booking.setId(bookingID);
        database.put(bookingID, booking);
        return bookingID;
    }

    @Override
    public boolean update(Booking booking) {
        Integer id = Integer.valueOf(booking.getId());
        if (!database.containsKey(id)) return false;

        database.replace(id, booking);
        return true;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        if (!database.containsKey(key)) return false;

        database.remove(key);
        return true;
    }

    @Override
    public boolean delete(Booking booking) {
        return deleteByKey(Integer.valueOf(booking.getId()));
    }

    @Override
    public Optional<Booking> findOne(Integer key) {
        if (!database.containsKey(key)) return Optional.empty();

        return Optional.of(database.get(key));
    }

    @Override
    public List<Booking> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Optional<Booking> findOneByDateAndUser(LocalDate date, User user) {
        return database.values().stream()
                .filter(booking -> booking.getDate().equals(date))
                .filter(booking -> booking.getUser().equals(user))
                .findAny();
    }
}
