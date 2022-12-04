package br.ifsp.edu.blackbearbarbearia.application.repository;

import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.client.ClientDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteClientDAO implements ClientDAO {
    @Override
    public Integer create(Client type) {
        String sql = """
                INSERT INTO client(
                    name,
                    email,
                    phone
                ) VALUES (?, ?, ?)
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setString(1, type.getName());
            stmt.setString(2, type.getEmail());
            stmt.setString(3, type.getPhone());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Optional<Client> created = findOneByEmail(type.getEmail());

        return created.map(Client::getId).orElse(null);
    }

    @Override
    public Boolean update(Client type) {
        String sql = """
                UPDATE client SET
                    name = ?,
                    email = ?,
                    phone = ?
                WHERE id = ?
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setString(1, type.getName());
            stmt.setString(2, type.getEmail());
            stmt.setString(3, type.getPhone());
            stmt.setInt(4, type.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Optional<Client> updated = findOne(type.getId());

        return type.equals(updated.get());
    }

    @Override
    public Boolean deleteByKey(Integer key) {
        String sql = """
                DELETE FROM client WHERE id = ?
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            stmt.setInt(1, key);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Optional<Client> deleted = findOne(key);
        return deleted.isEmpty();
    }

    @Override
    public Boolean delete(Client type) {
        return deleteByKey(type.getId());
    }

    @Override
    public Optional<Client> findOne(Integer key) {
        String sql = "SELECT * FROM client WHERE id = ?";
        Optional<Client> client = Optional.empty();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setInt(1, key);

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final String name = result.getString("name");
                final String email = result.getString("email");
                final String phone = result.getString("phone");

                client = Optional.of(new Client(key, name, email, phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client;
    }

    @Override
    public List<Client> findAll() {
        final List<Client> clients = new ArrayList<>();

        try {
            String sql = "SELECT * FROM client";
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final Integer id = result.getInt("id");
                final String name = result.getString("name");
                final String email = result.getString("email");
                final String phone = result.getString("phone");

                final Client client = new Client(id, name, email, phone);

                clients.add(client);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Optional<Client> findOneByEmail(String email) {
        String sql = "SELECT * FROM client WHERE email = ?";
        Optional<Client> client = Optional.empty();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setString(1, email);

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final Integer id = result.getInt("id");
                final String name = result.getString("name");
                final String phone = result.getString("phone");

                client = Optional.of(new Client(id, name, email, phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client;
    }
}
