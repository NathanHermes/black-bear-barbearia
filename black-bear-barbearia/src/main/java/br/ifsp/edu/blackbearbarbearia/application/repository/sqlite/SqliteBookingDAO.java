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
    public Boolean create(Booking newBooking) {
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

            stmt.setString(1, String.valueOf(newBooking.getNoFormattedDate()));
            stmt.setString(2, String.valueOf(newBooking.getHour()));
            stmt.setString(3, newBooking.isPaid());
            stmt.setInt(4, newBooking.getInfoClient().getId());
            stmt.setInt(5, newBooking.getInfoService().getId());
            stmt.setInt(6, newBooking.getInfoEmployee().getId());
            stmt.setInt(7, 1);

            if (stmt.executeUpdate() > 0)
                return Boolean.TRUE;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Boolean.FALSE;
    }

    @Override
    public Boolean update(Booking type) {
        return null;
    }

    @Override
    public Boolean updateStatus(Integer bookingID, Integer statusID, String paid) {
        String sql = "UPDATE booking SET paid = ?, statusId = ? WHERE id = ?";
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setString(1, paid);
            stmt.setInt(2, statusID);
            stmt.setInt(3, bookingID);

            if (stmt.executeUpdate() > 0)
                return Boolean.TRUE;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean deleteByKey(Integer key) {
        return null;
    }

    @Override
    public Boolean delete(Booking type) {
        return null;
    }

    @Override
    public Optional<Booking> findOne(Integer id) {
        String sql = "SELECT * FROM booking WHERE id = ?";
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            stmt.setInt(1, id);

            final ResultSet result = stmt.executeQuery();
            if(result.next()){
                final String date = result.getString("date");
                final String hour = result.getString("hour");
                final Boolean paid = result.getBoolean("paid");
                final Integer clientId = result.getInt("clientId");
                final Integer serviceId = result.getInt("serviceId");
                final Integer userId = result.getInt("userId");
                final Integer statusId = result.getInt("statusId");

                Client client = null;
                if (clientDAO.findOne(clientId).isPresent())
                    client = clientDAO.findOne(clientId).get();
                Service service = null;
                if (serviceDAO.findOne(serviceId).isPresent())
                    service = serviceDAO.findOne(serviceId).get();
                User employee = null;
                if ( userDAO.findOne(userId).isPresent())
                    employee =  userDAO.findOne(userId).get();
                Status status = null;
                if (statusDAO.findOne(statusId).isPresent())
                    status = statusDAO.findOne(statusId).get();

                return Optional.of(new Booking(id, Date.valueOf(date), Time.valueOf(hour), paid, client, service, employee, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Booking> findAll() {
        String sql = "SELECT * FROM booking";
        final List<Booking> bookings = new ArrayList<>();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            final ResultSet result = stmt.executeQuery();
            while(result.next()){
                final Integer id = result.getInt("id");
                final String date = result.getString("date");
                final String hour = result.getString("hour");
                final Boolean paid = result.getBoolean("paid");
                final Integer clientId = result.getInt("clientId");
                final Integer serviceId = result.getInt("serviceId");
                final Integer userId = result.getInt("userId");
                final Integer statusId = result.getInt("statusId");

                Client client = null;
                if (clientDAO.findOne(clientId).isPresent())
                    client = clientDAO.findOne(clientId).get();
                Service service = null;
                if (serviceDAO.findOne(serviceId).isPresent())
                    service = serviceDAO.findOne(serviceId).get();
                User employee = null;
                if ( userDAO.findOne(userId).isPresent())
                    employee =  userDAO.findOne(userId).get();
                Status status = null;
                if (statusDAO.findOne(statusId).isPresent())
                    status = statusDAO.findOne(statusId).get();

                final Booking booking = new Booking(id, Date.valueOf(date), Time.valueOf(hour), paid, client, service, employee, status);
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> findOneByDateAndUser(Date date, Integer userId) {
        String sql = "SELECT * FROM booking WHERE date = ? AND userId = ?";
        List<Booking> bookings = new ArrayList<>();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setString(1, date.toString());
            stmt.setInt(2, userId);

            final ResultSet result = stmt.executeQuery();
            while(result.next()){
                final Integer id = result.getInt("id");
                final String hour = result.getString("hour");
                final Boolean paid = result.getBoolean("paid");
                final Integer clientId = result.getInt("clientId");
                final Integer serviceId = result.getInt("serviceId");
                final Integer statusId = result.getInt("statusId");

                Client client = null;
                if (clientDAO.findOne(clientId).isPresent())
                    client = clientDAO.findOne(clientId).get();
                Service service = null;
                if (serviceDAO.findOne(serviceId).isPresent())
                    service = serviceDAO.findOne(serviceId).get();
                User employee = null;
                if ( userDAO.findOne(userId).isPresent())
                    employee =  userDAO.findOne(userId).get();
                Status status = null;
                if (statusDAO.findOne(statusId).isPresent())
                    status = statusDAO.findOne(statusId).get();

                final Booking booking = new Booking(id, date, Time.valueOf(hour), paid, client, service, employee, status);

                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
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
                final String hour = result.getString("hour");
                final Boolean paid = result.getBoolean("paid");
                final Integer clientId = result.getInt("clientId");
                final Integer serviceId = result.getInt("serviceId");
                final Integer userId = result.getInt("userId");
                final Integer statusId = result.getInt("statusId");

                Client client = null;
                if (clientDAO.findOne(clientId).isPresent())
                    client = clientDAO.findOne(clientId).get();
                Service service = null;
                if (serviceDAO.findOne(serviceId).isPresent())
                    service = serviceDAO.findOne(serviceId).get();
                User employee = null;
                if ( userDAO.findOne(userId).isPresent())
                    employee =  userDAO.findOne(userId).get();
                Status status = null;
                if (statusDAO.findOne(statusId).isPresent())
                    status = statusDAO.findOne(statusId).get();

                final Booking booking = new Booking(id, date, Time.valueOf(hour), paid, client, service, employee, status);

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