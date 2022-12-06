package br.ifsp.edu.blackbearbarbearia.application.repository.sqlite;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Status;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.booking.StatusDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class SqliteStatusDAO implements StatusDAO {
    @Override
    public Optional<Status> findOne(Integer key) {
        String sql = "SELECT * FROM status WHERE id = ?";
        Optional<Status> status = Optional.empty();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setInt(1, key);

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final String name = result.getString("name");

                status = Optional.of(Status.valueOf(name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public Optional<Integer> findId(Status entity) {
        String sql = "SELECT * FROM status WHERE name = ?";
        Optional<Integer> id = Optional.empty();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setString(1, entity.name());

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
