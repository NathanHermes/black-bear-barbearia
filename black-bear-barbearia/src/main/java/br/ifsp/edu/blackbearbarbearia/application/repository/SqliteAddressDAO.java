package br.ifsp.edu.blackbearbarbearia.application.repository;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Address;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.AddressDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqliteAddressDAO implements AddressDAO {
    @Override
    public Integer create(Integer userId, Address type) {
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

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Optional<Address> created = findOneByUserId(userId);
        return created.map(Address::getId).orElse(null);
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

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Optional<Address> updated = findOne(type.getId());
        return type.equals(updated.get());
    }

    @Override
    public Boolean deleteByKey(Integer key) {
        String sql = """
                DELETE FROM address WHERE id = ?
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            stmt.setInt(1, key);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Optional<Address> deleted = findOne(key);
        return deleted.isPresent();
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

        Optional<Address> deleted = findOneByUserId(userId);
        return deleted.isEmpty();
    }

    @Override
    public Boolean delete(Address type) {
        return deleteByKey(type.getId());
    }

    @Override
    public Optional<Address> findOne(Integer key) {
        String sql = "SELECT * FROM address WHERE id = ?";
        Optional<Address> address = Optional.empty();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setInt(1, key);

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final String street = result.getString("address");
                final String number = result.getString("number");
                final String complement = result.getString("complement");
                final String district = result.getString("district");
                final String city = result.getString("city");

                address = Optional.of(new Address(key, street, number, complement, district, city));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address;
    }

    @Override
    public Optional<Address> findOneByUserId(Integer userId) {
        String sql = "SELECT * FROM address WHERE userId = ?";
        Optional<Address> address = Optional.empty();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setInt(1, userId);

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final Integer id = result.getInt("id");
                final String street = result.getString("address");
                final String number = result.getString("number");
                final String complement = result.getString("complement");
                final String district = result.getString("district");
                final String city = result.getString("city");

                address = Optional.of(new Address(id, street, number, complement, district, city));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return address;
    }
}
