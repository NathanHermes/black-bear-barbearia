package br.ifsp.edu.blackbearbarbearia.application.repository;

import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Type;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.ServiceDAO;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteServiceDAO implements ServiceDAO {
    final SqliteServiceTypeDAO serviceTypeDAO = new SqliteServiceTypeDAO();

    @Override
    public Integer create(Service type) {
        String sql = """
                INSERT INTO service(
                    name,
                    price,
                    comissionPercentage,
                    taxPercentage,
                    active
                ) VALUES (?, ?, ?, ?, ?)
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setString(1, type.getName());
            stmt.setBigDecimal(2, type.getPrice());
            stmt.setBigDecimal(3, type.getComissionPercentage());
            stmt.setBigDecimal(4, type.getTaxPercentage());
            stmt.setBoolean(5, type.getActive());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Optional<Integer> id = findKeyByName(type.getName());

        serviceTypeDAO.create(id.get(), type.getTypes());

        return id.get();
    }

    @Override
    public Boolean update(Service type) {
        String sql = """
                UPDATE service SET
                    name = ?,
                    price = ?,
                    comissionPercentage = ?,
                    taxPercentage = ?,
                    active = ?
                WHERE id = ?
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setString(1, type.getName());
            stmt.setBigDecimal(2, type.getPrice());
            stmt.setBigDecimal(3, type.getComissionPercentage());
            stmt.setBigDecimal(4, type.getTaxPercentage());
            stmt.setBoolean(5, type.getActive());
            stmt.setInt(6, type.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        serviceTypeDAO.update(type.getId(), type.getTypes());
        Optional<Service> updated = findOne(type.getId());

        return type.equals(updated.get());
    }

    @Override
    public Boolean deleteByKey(Integer key) {
        String sql = """
                DELETE FROM service WHERE id = ?
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            stmt.setInt(1, key);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        serviceTypeDAO.deleteByServiceId(key);

        Optional<Service> deleted = findOne(key);
        return deleted.isPresent();
    }

    @Override
    public Boolean delete(Service type) {
        return deleteByKey(type.getId());
    }

    @Override
    public Optional<Service> findOne(Integer key) {
        String sql = "SELECT * FROM service WHERE id = ?";
        Optional<Service> service = Optional.empty();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setInt(1, key);

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final String name = result.getString("name");
                final BigDecimal price = BigDecimal.valueOf(result.getDouble("price"));
                final BigDecimal comissionPercentage = BigDecimal.valueOf(result.getDouble("comissionPercentage"));
                final BigDecimal taxPercentage = BigDecimal.valueOf(result.getDouble("taxPercentage"));
                final Boolean active = result.getBoolean("active");

                final List<Type> types = serviceTypeDAO.findByServiceId(key);

                service = Optional.of(new Service(key, name, price, comissionPercentage, taxPercentage, active, types));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return service;
    }

    @Override
    public List<Service> findAll() {
        List<Service> services = new ArrayList<>();

        try {
            String sql = "SELECT * FROM service";
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final Integer id = result.getInt("id");
                final String name = result.getString("name");
                final BigDecimal price = BigDecimal.valueOf(result.getDouble("price"));
                final BigDecimal comissionPercentage = BigDecimal.valueOf(result.getDouble("comissionPercentage"));
                final BigDecimal taxPercentage = BigDecimal.valueOf(result.getDouble("taxPercentage"));
                final Boolean active = result.getBoolean("active");

                final List<Type> types = serviceTypeDAO.findByServiceId(id);

                final Service service = new Service(id, name, price, comissionPercentage, taxPercentage, active, types);

                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return services;
    }

    @Override
    public Optional<Service> findOneByName(String name) {
        String sql = "SELECT * FROM service WHERE name = ?";
        Optional<Service> service = Optional.empty();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setString(1, name);

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final Integer id = result.getInt("id");
                final BigDecimal price = BigDecimal.valueOf(result.getDouble("price"));
                final BigDecimal comissionPercentage = BigDecimal.valueOf(result.getDouble("comissionPercentage"));
                final BigDecimal taxPercentage = BigDecimal.valueOf(result.getDouble("taxPercentage"));
                final Boolean active = result.getBoolean("active");

                final List<Type> types = serviceTypeDAO.findByServiceId(id);

                service = Optional.of(new Service(id, name, price, comissionPercentage, taxPercentage, active, types));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return service;
    }

    public Optional<Integer> findKeyByName(String name) {
        String sql = "SELECT id FROM service WHERE name = ?";
        Optional<Integer> id = Optional.empty();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setString(1, name);

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