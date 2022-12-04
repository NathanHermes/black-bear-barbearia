package br.ifsp.edu.blackbearbarbearia.application.repository;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Address;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Role;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.*;

public class SqliteUserDAO implements UserDAO {
    final SqliteAddressDAO addressDAO = new SqliteAddressDAO();
    final SqliteUserDayDAO userDayDAO = new SqliteUserDayDAO();

    @Override
    public Integer create(User type) {
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
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            final Boolean admin;

            if(type.getRole().equals(Role.ADMIN)) admin = true;
            else admin = false;

            stmt.setString(1, type.getFullName());
            stmt.setString(2, type.getEmail());
            stmt.setString(3, type.getPhone());
            stmt.setString(4, type.getLogin());
            stmt.setString(5, type.getPasswordHash());
            stmt.setBoolean(6, type.isActive());
            stmt.setBoolean(7, admin);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Optional<Integer> id = findKeyByFullname(type.getFullName());

        addressDAO.create(id.get(), type.getAddress());
        userDayDAO.create(id.get(), type.getDays());

        return id.get();
    }

    @Override
    public Boolean update(User type) {
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
            final Boolean admin;

            if(type.getRole().equals(Role.ADMIN)) admin = true;
            else admin = false;

            stmt.setString(1, type.getFullName());
            stmt.setString(2, type.getEmail());
            stmt.setString(3, type.getPhone());
            stmt.setString(4, type.getLogin());
            stmt.setString(5, type.getPasswordHash());
            stmt.setBoolean(6, type.isActive());
            stmt.setBoolean(7, admin);
            stmt.setInt(8, type.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        addressDAO.update(type.getId(), type.getAddress());
        userDayDAO.update(type.getId(), type.getDays());
        Optional<User> updated = findOne(type.getId());

        return type.equals(updated.get());
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
        return deleted.isPresent();
    }

    @Override
    public Boolean delete(User type) {
        return deleteByKey(type.getId());
    }

    @Override
    public Optional<User> findOne(Integer key) {
        String sql = "SELECT * FROM user WHERE id = ?";
        Optional<User> user = Optional.empty();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setInt(1, key);

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final String fullName = result.getString("fullName");
                final String email = result.getString("email");
                final String phone = result.getString("phone");
                final String login = result.getString("login");
                final String passwordHash = result.getString("passwordHash");
                final Boolean active = result.getBoolean("active");
                final Boolean admin = result.getBoolean("admin");

                Optional<Address> address = addressDAO.findOneByUserId(key);

                final Role role;
                if(admin) role = Role.ADMIN;
                else role = Role.EMPLOYEE;

                final List<DayOfWeek> days = userDayDAO.findByUserId(key);

                user = Optional.of(new User(key, fullName, email, phone, address.get(), login, passwordHash, active, role, days));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
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
                final Boolean admin = result.getBoolean("admin");

                Optional<Address> address = addressDAO.findOneByUserId(id);

                final Role role;
                if(admin) role = Role.ADMIN;
                else role = Role.EMPLOYEE;

                final List<DayOfWeek> days = userDayDAO.findByUserId(id);

                final User user = new User(id, fullName, email, phone, address.get(), login, passwordHash, active, role, days);

                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
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
