package br.ifsp.edu.blackbearbarbearia.application.repository.sqlite;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Address;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.AddressDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqliteAddressDAO implements AddressDAO {
    @Override
    public Boolean create(Integer userId, Address type) {
        String sql = """
                INSERT INTO address(
                    address,
                    number,
                    complement,
                    district,
                    city,
                    userId
                ) VALUES (?, ?, ?, ?, ?, ?)
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setString(1, type.getAddress());
            stmt.setString(2, type.getNumber());
            stmt.setString(3, type.getComplement());
            stmt.setString(4, type.getDistrict());
            stmt.setString(5, type.getCity());
            stmt.setInt(6, userId);

            if (stmt.executeUpdate() > 0)
                return Boolean.TRUE;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean update(Integer userId, Address type) {
        String sql = """
                UPDATE address SET
                    address = ?,
                    number = ?,
                    complement = ?,
                    district = ?,
                    city = ?
                WHERE userId = ?
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setString(1, type.getAddress());
            stmt.setString(2, type.getNumber());
            stmt.setString(3, type.getComplement());
            stmt.setString(4, type.getDistrict());
            stmt.setString(5, type.getCity());
            stmt.setInt(6, userId);

            if (stmt.executeUpdate() > 0)
                return Boolean.TRUE;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean deleteById(Integer id) {
        String sql = """
                DELETE FROM address WHERE id = ?
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            stmt.setInt(1, id);
            if (stmt.executeUpdate() > 0)
                return Boolean.TRUE;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean deleteByUserId(Integer userId) {
        String sql = """
                DELETE FROM address WHERE userId = ?
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Optional<Address> deleted = findByUserId(userId);
        return deleted.isEmpty();
    }

    @Override
    public Boolean delete(Address type) {
        return deleteById(type.getId());
    }

    @Override
    public Optional<Address> findOne(Integer id) {
        String sql = "SELECT * FROM address WHERE id = ?";

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            stmt.setInt(1, id);

            final ResultSet result = stmt.executeQuery();
            if(result.next()){
                final String street = result.getString("address");
                final String number = result.getString("number");
                final String complement = result.getString("complement");
                final String district = result.getString("district");
                final String city = result.getString("city");

                return Optional.of(new Address(id, street, number, complement, district, city));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Address> findByUserId(Integer userId) {
        String sql = "SELECT * FROM address WHERE userId = ?";

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setInt(1, userId);

            final ResultSet result = stmt.executeQuery();

            if(result.next()){
                final Integer id = result.getInt("id");
                final String street = result.getString("address");
                final String number = result.getString("number");
                final String complement = result.getString("complement");
                final String district = result.getString("district");
                final String city = result.getString("city");

                return Optional.of(new Address(id, street, number, complement, district, city));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
