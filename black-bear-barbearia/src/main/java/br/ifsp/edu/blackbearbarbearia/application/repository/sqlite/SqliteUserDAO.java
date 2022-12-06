package br.ifsp.edu.blackbearbarbearia.application.repository.sqlite;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Address;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Role;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.UserBuilder;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.*;

public class SqliteUserDAO implements UserDAO {
    private final SqliteAddressDAO addressDAO = new SqliteAddressDAO();
    private final SqliteUserDayDAO userDayDAO = new SqliteUserDayDAO();

    @Override
    public Boolean create(User employee) {
        String sql = """
                INSERT INTO user(
                    fullName,
                    email,
                    phone,
                    login,
                    passwordHash,
                    active,
                    admin
                ) VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        final PreparedStatement stmt;
        try {
            stmt = ConnectionFactory.createPreparedStatement(sql);
            final boolean admin;

            if(employee.getRole().equals(Role.ADMIN)) admin = true;
            else admin = false;

            stmt.setString(1, employee.getFullName());
            stmt.setString(2, employee.getEmail());
            stmt.setString(3, employee.getPhone());
            stmt.setString(4, employee.getLogin());
            stmt.setString(5, employee.getPasswordHash());
            stmt.setBoolean(6, employee.isActive());
            stmt.setBoolean(7, admin);

            if (stmt.executeUpdate() == 0)
                return Boolean.FALSE;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (findIDByFullname(employee.getFullName()).isEmpty())
            throw new IllegalArgumentException("Employee not found");
        Integer id = findIDByFullname(employee.getFullName()).get();

        Boolean addressDAOResponse = addressDAO.create(id, employee.getAddress());
        if (addressDAOResponse.equals(Boolean.FALSE))
            return Boolean.FALSE;
        Boolean userDayDAOResponse = userDayDAO.create(id, employee.getDays());
        if (userDayDAOResponse.equals(Boolean.FALSE))
            return Boolean.FALSE;

        return Boolean.TRUE;
    }

    @Override
    public Boolean update(User employee) {
        String sql = """
                UPDATE user SET
                    fullName = ?,
                    email = ?,
                    phone = ?,
                    login = ?,
                    passwordHash = ?,
                    active = ?,
                    admin = ?
                WHERE id = ?
                """;

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            final boolean admin;

            if (employee.getRole().equals(Role.ADMIN)) admin = true;
            else admin = false;

            stmt.setString(1, employee.getFullName());
            stmt.setString(2, employee.getEmail());
            stmt.setString(3, employee.getPhone());
            stmt.setString(4, employee.getLogin());
            stmt.setString(5, employee.getPasswordHash());
            stmt.setBoolean(6, employee.isActive());
            stmt.setBoolean(7, admin);
            stmt.setInt(8, employee.getId());

            if (stmt.executeUpdate() == 0)
                return Boolean.FALSE;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Boolean addressDAOResponse = addressDAO.update(employee.getId(), employee.getAddress());
        if (addressDAOResponse.equals(Boolean.FALSE))
            return Boolean.FALSE;
        Boolean userDayDAOResponse = userDayDAO.update(employee.getId(), employee.getDays());
        if (userDayDAOResponse.equals(Boolean.FALSE))
            return Boolean.FALSE;

        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteByKey(Integer key) {
        String sql = """
                DELETE FROM user WHERE id = ?
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            stmt.setInt(1, key);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        addressDAO.deleteByUserId(key);
        userDayDAO.deleteByUserId(key);

        Optional<User> deleted = findOne(key);
        return deleted.isEmpty();
    }

    @Override
    public Boolean delete(User type) {
        return deleteByKey(type.getId());
    }

    @Override
    public Optional<User> findOne(Integer userID) {
        String sql = "SELECT * FROM user WHERE id = ?";

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            stmt.setInt(1, userID);

            final ResultSet result = stmt.executeQuery();

            if(result.next()){
                final String fullName = result.getString("fullName");
                final String email = result.getString("email");
                final String phone = result.getString("phone");
                final String login = result.getString("login");
                final String passwordHash = result.getString("passwordHash");
                final Boolean active = result.getBoolean("active");
                final boolean admin = result.getBoolean("admin");

                final Role role= admin ? Role.ADMIN : Role.EMPLOYEE;

                if (addressDAO.findByUserId(userID).isEmpty())
                    throw new EntityNotFoundException("Address not found");
                Address address = addressDAO.findByUserId(userID).get();

                final List<DayOfWeek> days = userDayDAO.findByUserId(userID);

                UserBuilder userBuilder = new UserBuilder();
                userBuilder.setId(userID);
                userBuilder.setFullName(fullName);
                userBuilder.setEmail(email);
                userBuilder.setPhone(phone);
                userBuilder.setLogin(login);
                userBuilder.setPasswordHash(passwordHash);
                userBuilder.setActive(active);
                userBuilder.setRole(role);
                userBuilder.setAddress(address);
                userBuilder.setDays(days);

                return Optional.of(userBuilder.getResult());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        final List<User> users = new ArrayList<>();

        try {
            String sql = "SELECT * FROM user";
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final Integer id = result.getInt("id");
                final String fullName = result.getString("fullName");
                final String email = result.getString("email");
                final String phone = result.getString("phone");
                final String login = result.getString("login");
                final String passwordHash = result.getString("passwordHash");
                final Boolean active = result.getBoolean("active");
                final boolean admin = result.getBoolean("admin");

                final Role role = admin ? Role.ADMIN : Role.EMPLOYEE;

                if (addressDAO.findByUserId(id).isEmpty())
                    throw new EntityNotFoundException("Address not found");
                Address address = addressDAO.findByUserId(id).get();

                final List<DayOfWeek> days = userDayDAO.findByUserId(id);

                UserBuilder userBuilder = new UserBuilder();
                userBuilder.setId(id);
                userBuilder.setFullName(fullName);
                userBuilder.setEmail(email);
                userBuilder.setPhone(phone);
                userBuilder.setLogin(login);
                userBuilder.setPasswordHash(passwordHash);
                userBuilder.setActive(active);
                userBuilder.setRole(role);
                userBuilder.setAddress(address);
                userBuilder.setDays(days);

                users.add(userBuilder.getResult());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Optional<User> findOneByLogin(String login) {
        String sql = "SELECT * FROM user WHERE login = ?";
        Optional<User> user = Optional.empty();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setString(1, login);

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final Integer id = result.getInt("id");
                final String fullName = result.getString("fullName");
                final String email = result.getString("email");
                final String phone = result.getString("phone");
                final String passwordHash = result.getString("passwordHash");
                final Boolean active = result.getBoolean("active");
                final Boolean admin = result.getBoolean("admin");

                Optional<Address> address = addressDAO.findOneByUserId(id);

                final Role role;
                if(admin) role = Role.ADMIN;
                else role = Role.EMPLOYEE;

                final List<DayOfWeek> days = userDayDAO.findByUserId(id);

                user = Optional.of(new User(id, fullName, email, phone, address.get(), login, passwordHash, active, role, days));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public Optional<User> findOneByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        Optional<User> user = Optional.empty();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setString(1, email);

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final Integer id = result.getInt("id");
                final String fullName = result.getString("fullName");
                final String phone = result.getString("phone");
                final String login = result.getString("login");
                final String passwordHash = result.getString("passwordHash");
                final Boolean active = result.getBoolean("active");
                final Boolean admin = result.getBoolean("admin");

                Optional<Address> address = addressDAO.findOneByUserId(id);
                if(address.isEmpty()) address = null;

                final Role role;
                if(admin) role = Role.ADMIN;
                else role = Role.EMPLOYEE;

                final List<DayOfWeek> days = userDayDAO.findByUserId(id);

                user = Optional.of(new User(id, fullName, email, phone, address.get(), login, passwordHash, active, role, days));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> findOneByDay(DayOfWeek day) {
        return null;
    }

    private Optional<Integer> findKeyByFullname(String fullName) {
        String sql = "SELECT id FROM user WHERE fullName = ?";
        Optional<Integer> id = Optional.empty();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setString(1, fullName);

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                id = Optional.of(result.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }
}
