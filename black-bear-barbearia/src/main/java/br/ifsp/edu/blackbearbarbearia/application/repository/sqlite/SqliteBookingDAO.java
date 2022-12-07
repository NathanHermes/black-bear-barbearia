package br.ifsp.edu.blackbearbarbearia.application.repository.sqlite;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Status;
import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.booking.BookingDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteBookingDAO implements BookingDAO {
    final SqliteStatusDAO statusDAO = new SqliteStatusDAO();
    final SqliteClientDAO clientDAO = new SqliteClientDAO();
    final SqliteServiceDAO serviceDAO = new SqliteServiceDAO();
    final SqliteUserDAO userDAO = new SqliteUserDAO();

    @Override
    public Boolean create(Booking type) {
        String sql = """
                INSERT INTO booking(
                    date,
                    hour,
                    paid,
                    clientId,
                    serviceId,
                    userId,
                    statusId
                ) VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            final Optional<Integer> idStatus = statusDAO.findId(type.getStatus());

            stmt.setDate(1, type.getNoFormattedDate());
            stmt.setTime(2, type.getHour());
            stmt.setString(3, type.isPaid());
            stmt.setInt(4, type.getInfoClient().getId());
            stmt.setInt(5, type.getInfoService().getId());
            stmt.setInt(6, type.getInfoEmployee().getId());
            stmt.setInt(7, idStatus.get());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Boolean.TRUE;
    }

    @Override
    public Boolean update(Booking type) {
        String sql = """
                UPDATE booking SET
                    date = ?,
                    hour = ?,
                    paid = ?,
                    clientId = ?,
                    serviceId = ?,
                    userId = ?,
                    statusId = ?
                WHERE id = ?
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            final Optional<Integer> idStatus = statusDAO.findId(type.getStatus());

            stmt.setDate(1, type.getNoFormattedDate());
            stmt.setTime(2, type.getHour());
            stmt.setString(3, type.isPaid());
            stmt.setInt(4, type.getInfoClient().getId());
            stmt.setInt(5, type.getInfoService().getId());
            stmt.setInt(6, type.getInfoEmployee().getId());
            stmt.setInt(7, idStatus.get());
            stmt.setInt(8, type.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Optional<Booking> updated = findOne(type.getId());
        return type.equals(updated.get());
    }

    @Override
    public Boolean deleteByKey(Integer key) {
        String sql = """
                DELETE FROM booking WHERE id = ?
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            stmt.setInt(1, key);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Optional<Booking> deleted = findOne(key);
        return deleted.isEmpty();
    }

    @Override
    public Boolean delete(Booking type) {
        return deleteByKey(type.getId());
    }

    @Override
    public Optional<Booking> findOne(Integer key) {
        String sql = "SELECT * FROM booking WHERE id = ?";
        Optional<Booking> booking = Optional.empty();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setInt(1, key);

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final Date date = result.getDate("date");
                final Time hour = result.getTime("hour");
                final Boolean paid = result.getBoolean("paid");
                final Integer clientId = result.getInt("clientId");
                final Integer serviceId = result.getInt("serviceId");
                final Integer userId = result.getInt("userId");
                final Integer statusId = result.getInt("statusId");

                final Optional<Client> client = clientDAO.findOne(clientId);
                final Optional<Service> service = serviceDAO.findOne(serviceId);
                final Optional<User> user = userDAO.findOne(userId);
                final Optional<Status> status = statusDAO.findOne(statusId);

                booking = Optional.of(new Booking(key, date, hour, paid, client.get(), service.get(), user.get(), status.get()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booking;
    }

    @Override
    public List<Booking> findAll() {
        final List<Booking> bookings = new ArrayList<>();

        try {
            String sql = "SELECT * FROM booking";
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final Integer id = result.getInt("id");
                final Date date = result.getDate("date");
                final Time hour = result.getTime("hour");
                final Boolean paid = result.getBoolean("paid");
                final Integer clientId = result.getInt("clientId");
                final Integer serviceId = result.getInt("serviceId");
                final Integer userId = result.getInt("userId");
                final Integer statusId = result.getInt("statusId");

                final Optional<Client> client = clientDAO.findOne(clientId);
                final Optional<Service> service = serviceDAO.findOne(serviceId);
                final Optional<User> user = userDAO.findOne(userId);
                final Optional<Status> status = statusDAO.findOne(statusId);

                final Booking booking = new Booking(id, date, hour, paid, client.get(), service.get(), user.get(), status.get());

                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public Optional<Booking> findOneByDateAndUser(Date date, User user) {
        String sql = "SELECT * FROM booking WHERE date = ? AND userId = ?";
        Optional<Booking> booking = Optional.empty();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setDate(1, date);
            stmt.setInt(2, user.getId());

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final Integer id = result.getInt("id");
                final Time hour = result.getTime("hour");
                final Boolean paid = result.getBoolean("paid");
                final Integer clientId = result.getInt("clientId");
                final Integer serviceId = result.getInt("serviceId");
                final Integer statusId = result.getInt("statusId");

                final Optional<Client> client = clientDAO.findOne(clientId);
                final Optional<Service> service = serviceDAO.findOne(serviceId);
                final Optional<Status> status = statusDAO.findOne(statusId);

                booking = Optional.of(new Booking(id, date, hour, paid, client.get(), service.get(), user, status.get()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booking;
    }

    @Override
    public Optional<Booking> findOneByDate(Date date) {
        return Optional.empty();
    }

    @Override
    public List<Booking> findAllByDate(Date date) {
        final List<Booking> bookings = new ArrayList<>();

        try {
            String sql = "SELECT * FROM booking WHERE date = ?";

            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setDate(1, date);

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final Integer id = result.getInt("id");
                final Time hour = result.getTime("hour");
                final Boolean paid = result.getBoolean("paid");
                final Integer clientId = result.getInt("clientId");
                final Integer serviceId = result.getInt("serviceId");
                final Integer userId = result.getInt("userId");
                final Integer statusId = result.getInt("statusId");

                final Optional<Client> client = clientDAO.findOne(clientId);
                final Optional<Service> service = serviceDAO.findOne(serviceId);
                final Optional<User> user = userDAO.findOne(userId);
                final Optional<Status> status = statusDAO.findOne(statusId);

                final Booking booking = new Booking(id, date, hour, paid, client.get(), service.get(), user.get(), status.get());

                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> findAllByUserAndPeriod(User user, Date start, Date end) {
        final List<Booking> bookings = new ArrayList<>();

        try {
            String sql = "SELECT * FROM booking WHERE userId = ? AND date >= ? AND date <= end";

            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setInt(1, user.getId());
            stmt.setDate(2, start);
            stmt.setDate(3, end);

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final Integer id = result.getInt("id");
                final Date date = result.getDate("date");
                final Time hour = result.getTime("hour");
                final Boolean paid = result.getBoolean("paid");
                final Integer clientId = result.getInt("clientId");
                final Integer serviceId = result.getInt("serviceId");
                final Integer statusId = result.getInt("statusId");

                final Optional<Client> client = clientDAO.findOne(clientId);
                final Optional<Service> service = serviceDAO.findOne(serviceId);
                final Optional<Status> status = statusDAO.findOne(statusId);

                final Booking booking = new Booking(id, date, hour, paid, client.get(), service.get(), user, status.get());

                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> findAllByUser(User user) {
        final List<Booking> bookings = new ArrayList<>();

        try {
            String sql = "SELECT * FROM booking WHERE userId = ?";

            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setInt(1, user.getId());

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final Integer id = result.getInt("id");
                final Date date = result.getDate("date");
                final Time hour = result.getTime("hour");
                final Boolean paid = result.getBoolean("paid");
                final Integer clientId = result.getInt("clientId");
                final Integer serviceId = result.getInt("serviceId");
                final Integer statusId = result.getInt("statusId");

                final Optional<Client> client = clientDAO.findOne(clientId);
                final Optional<Service> service = serviceDAO.findOne(serviceId);
                final Optional<Status> status = statusDAO.findOne(statusId);

                final Booking booking = new Booking(id, date, hour, paid, client.get(), service.get(), user, status.get());

                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;    }

    @Override
    public List<Booking> findAllByPeriod(Date start, Date end) {
        final List<Booking> bookings = new ArrayList<>();

        try {
            String sql = "SELECT * FROM booking WHERE date >= ? AND date <= end";

            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setDate(1, start);
            stmt.setDate(2, end);

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final Integer id = result.getInt("id");
                final Date date = result.getDate("date");
                final Time hour = result.getTime("hour");
                final Boolean paid = result.getBoolean("paid");
                final Integer clientId = result.getInt("clientId");
                final Integer serviceId = result.getInt("serviceId");
                final Integer userId = result.getInt("userId");
                final Integer statusId = result.getInt("statusId");

                final Optional<Client> client = clientDAO.findOne(clientId);
                final Optional<Service> service = serviceDAO.findOne(serviceId);
                final Optional<User> user = userDAO.findOne(userId);
                final Optional<Status> status = statusDAO.findOne(statusId);

                final Booking booking = new Booking(id, date, hour, paid, client.get(), service.get(), user.get(), status.get());

                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
}