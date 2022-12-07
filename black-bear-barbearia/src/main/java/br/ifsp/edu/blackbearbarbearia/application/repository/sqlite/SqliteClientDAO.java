package br.ifsp.edu.blackbearbarbearia.application.repository.sqlite;

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
    public Boolean create(Client client) {
        String sql = """
                INSERT INTO client(
                    name,
                    email,
                    phone
                ) VALUES (?, ?, ?)
                """;

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setString(1, client.getName());
            stmt.setString(2, client.getEmail());
            stmt.setString(3, client.getPhone());

            if(stmt.executeUpdate() > 0) return Boolean.TRUE;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean update(Client client) {
        String sql = """
                UPDATE client SET
                    name = ?,
                    email = ?,
                    phone = ?
                WHERE id = ?
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setString(1, client.getName());
            stmt.setString(2, client.getEmail());
            stmt.setString(3, client.getPhone());
            stmt.setInt(4, client.getId());

            if(stmt.executeUpdate() > 0) return Boolean.TRUE;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean deleteByKey(Integer key) {
        /*
        * String sql = """
        *           DELETE FROM client WHERE id = ?
        *           """;
        *   try {
        *       final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
        *       stmt.setInt(1, key);
        *       stmt.executeUpdate();
        *   } catch (SQLException e) {
        *       e.printStackTrace();
        *   }
        *
        *   Optional<Client> deleted = findOne(key);
        *   return deleted.isEmpty();
        */
        return null;
    }

    @Override
    public Boolean delete(Client type) {
        /*
        * return deleteByKey(type.getId());
        */
        return null;
    }

    @Override
    public Optional<Client> findOne(Integer id) {
        String sql = "SELECT * FROM client WHERE id = ?";

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            stmt.setInt(1, id);

            final ResultSet result = stmt.executeQuery();
            if(result.next()){
                final String name = result.getString("name");
                final String email = result.getString("email");
                final String phone = result.getString("phone");

                return Optional.of(new Client(id, name, email, phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Client> findOneByName(String name) {
        String sql = "SELECT * FROM client WHERE name = ?";
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            stmt.setString(1, name);

            final ResultSet result = stmt.executeQuery();
            if (result.next()) {
                final Integer id = result.getInt("id");
                final String email = result.getString("email");
                final String phone = result.getString("phone");

                return Optional.of(new Client(id, name, email, phone));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
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
