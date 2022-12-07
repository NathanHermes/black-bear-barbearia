package br.ifsp.edu.blackbearbarbearia.application.repository.sqlite;

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
    public Boolean create(Service type) {
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

        return Boolean.TRUE;
    }

    @Override
    public Boolean update(Service type) {
        String sql = """
                UPDATE service SET
                    price = ?,
                    comissionPercentage = ?,
                    taxPercentage = ?,
                    active = ?
                WHERE id = ?
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setBigDecimal(1, type.getPrice());
            stmt.setBigDecimal(2, type.getComissionPercentage());
            stmt.setBigDecimal(3, type.getTaxPercentage());
            stmt.setBoolean(4, type.getActive());
            stmt.setInt(5, type.getId());

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
        return deleted.isEmpty();
    }

    @Override
    public Boolean delete(Service type) {
        return deleteByKey(type.getId());
    }

    @Override
    public Optional<Service> findOne(Integer id) {
        String sql = "SELECT * FROM service WHERE id = ?";

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            stmt.setInt(1, id);

            final ResultSet result = stmt.executeQuery();
            if(result.next()){
                final String name = result.getString("name");
                final double price = result.getDouble("price");
                final String commissionPercentage = result.getString("comissionPercentage").replace(",", ".");
                final String taxPercentage = result.getString("taxPercentage").replace(",", ".");
                final Boolean active = result.getBoolean("active");

                final List<Type> types = serviceTypeDAO.findByServiceId(id);

                double cp = Double.parseDouble(commissionPercentage);
                double tp = Double.parseDouble(taxPercentage);

                return Optional.of(new Service(id, name, BigDecimal.valueOf(price), BigDecimal.valueOf(cp), BigDecimal.valueOf(tp), active, types));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Service> findAll() {
        String sql = "SELECT * FROM service";
        List<Service> services = new ArrayList<>();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            final ResultSet result = stmt.executeQuery();
            while(result.next()){
                final Integer id = result.getInt("id");
                final String name = result.getString("name");
                final double price = result.getDouble("price");
                final String comissionPercentage = result.getString("comissionPercentage").replace(",", ".");
                final String taxPercentage = result.getString("taxPercentage").replace(",", ".");
                final Boolean active = result.getBoolean("active");

                final List<Type> types = serviceTypeDAO.findByServiceId(id);

                double cp = Double.parseDouble(comissionPercentage);
                double tp = Double.parseDouble(taxPercentage);

                final Service service = new Service(id, name, BigDecimal.valueOf(price), BigDecimal.valueOf(cp), BigDecimal.valueOf(tp), active, types);

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

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            stmt.setString(1, name);

            final ResultSet result = stmt.executeQuery();
            if(result.next()){
                final Integer id = result.getInt("id");
                final double price = result.getDouble("price");
                final double commissionPercentage = result.getDouble("comissionPercentage");
                final BigDecimal taxPercentage = BigDecimal.valueOf(result.getDouble("taxPercentage"));
                final Boolean active = result.getBoolean("active");

                final List<Type> types = serviceTypeDAO.findByServiceId(id);

                return Optional.of(new Service(id, name, BigDecimal.valueOf(price), BigDecimal.valueOf(commissionPercentage), taxPercentage, active, types));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
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