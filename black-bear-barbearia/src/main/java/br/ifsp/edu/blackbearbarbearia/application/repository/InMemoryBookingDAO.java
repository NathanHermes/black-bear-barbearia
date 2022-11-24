package br.ifsp.edu.blackbearbarbearia.application.repository;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.booking.BookingDAO;

import java.time.LocalDate;
import java.util.*;

public class InMemoryBookingDAO implements BookingDAO {
    private static final Map<Integer, Booking> database = new LinkedHashMap<>();
    private Integer bookingID = 0;

    @Override
    public Integer create(Booking booking) {
        bookingID++;
        booking.setId(bookingID);
        database.put(bookingID, booking);
        return bookingID;
    }

    @Override
    public Boolean update(Booking booking) {
        Integer id = booking.getId();
        if (!database.containsKey(id)) return false;

        database.replace(id, booking);
        return true;
    }

    @Override
    public Boolean deleteByKey(Integer key) {
        if (!database.containsKey(key)) return false;

        database.remove(key);
        return true;
    }

    @Override
    public Boolean delete(Booking booking) {
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

    @Override
    public Optional<Booking> findOneByDate(LocalDate date) {
        return database.values().stream()
                .filter(booking -> booking.getDate().equals(date))
                .findAny();
    }

    @Override
    public List<Booking> findAllByDate(LocalDate date) {
        return database.values().stream()
                .filter(booking -> booking.getDate().equals(date))
                .toList();
    }

    @Override
    public List<Booking> findAllByUserAndPeriod(User user, LocalDate start, LocalDate end) {
        return database.values().stream()
                .filter(booking -> booking.getDate().equals(user))
                .filter(booking -> booking.getDate().isAfter(start))
                .filter(booking -> booking.getDate().isBefore(end))
                .toList();
    }

    @Override
    public List<Booking> findAllByUser(User user) {
        return database.values().stream()
                .filter(booking -> booking.getDate().equals(user))
                .toList();
    }

    public List<Booking> findAllByPeriod(LocalDate start, LocalDate end) {
        return database.values().stream()
                .filter(booking -> booking.getDate().isAfter(start))
                .filter(booking -> booking.getDate().isBefore(end))
                .toList();
    }
}
