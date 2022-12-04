package br.ifsp.edu.blackbearbarbearia.application.repository;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Address;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.AddressDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqliteAddressDAO implements AddressDAO {
    @Override
    public Integer create(Address type) {
        return null;
    }

    @Override
    public Boolean update(Address type) {
        return null;
    }

    @Override
    public Boolean deleteByKey(Integer key) {
        return null;
    }

    @Override
    public Boolean delete(Address type) {
        return null;
    }

    @Override
    public Optional<Address> findOne(Integer key) {
        return Optional.empty();
    }

    @Override
    public List<Address> findAll() {
        return null;
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
