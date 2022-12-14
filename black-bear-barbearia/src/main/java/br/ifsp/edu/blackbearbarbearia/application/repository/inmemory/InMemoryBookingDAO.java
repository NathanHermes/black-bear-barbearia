package br.ifsp.edu.blackbearbarbearia.application.repository.inmemory;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.booking.BookingDAO;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class InMemoryBookingDAO implements BookingDAO {
    private static final Map<Integer, Booking> database = new LinkedHashMap<>();
    private Integer bookingID = 0;

    @Override
    public Boolean create(Booking booking) {
        bookingID++;
        booking.setId(bookingID);
        if (database.put(bookingID, booking) != null)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
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

//    @Override
//    public Optional<Booking> findOneByDateAndUser(Date date, User user) {
//        return database.values().stream()
//                .filter(booking -> booking.getDate().equals(date))
//                .filter(booking -> booking.getEmployee().equals(user))
//                .findAny();
//    }
//
//    @Override
//    public Optional<Booking> findOneByDate(Date date) {
//        return database.values().stream()
//                .filter(booking -> booking.getDate().equals(date))
//                .findAny();
//    }
//
//    @Override
//    public List<Booking> findAllByDate(Date date) {
//        return database.values().stream()
//                .filter(booking -> booking.getDate().equals(date))
//                .toList();
//    }
//
//    @Override
//    public List<Booking> findAllByUserAndPeriod(User user, Date start, Date end) {
//        return database.values().stream()
//                .filter(booking -> booking.getNoFormattedDate().equals(user))
//                .filter(booking -> booking.getNoFormattedDate().after(start))
//                .filter(booking -> booking.getNoFormattedDate().before(end))
//                .toList();
//    }

    @Override
    public Boolean updateStatus(Integer bookingID, Integer statusID, String paid) {
        return null;
    }

    @Override
    public List<Booking> findOneByDateAndUser(Date date, Integer user) {
        return database.values().stream()
                .filter(booking -> booking.getDate().equals(date))
                .filter(booking -> booking.getEmployee().equals(user)).toList();
    }

    @Override
    public Optional<Booking> findOneByDate(Date date) {
        return Optional.empty();
    }

    @Override
    public List<Booking> findAllByDate(Date date) {
        return null;
    }

    @Override
    public List<Booking> findAllByUserAndPeriod(User user, Date start, Date end) {
        return null;
    }

    @Override
    public List<Booking> findAllByUser(User user) {
        return database.values().stream()
                .filter(booking -> booking.getDate().equals(user))
                .toList();
    }

    @Override
    public List<Booking> findAllByPeriod(Date start, Date end) {
        return null;
    }

    public List<Booking> findAllByPeriod(LocalDate start, LocalDate end) {
        return database.values().stream()
                .filter(booking -> booking.getNoFormattedDate().toLocalDate().isAfter(start))
                .filter(booking -> booking.getNoFormattedDate().toLocalDate().isBefore(end))
                .toList();
    }
}
