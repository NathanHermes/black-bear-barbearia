package br.ifsp.edu.blackbearbarbearia.application.repository;

import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Type;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.TypeDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class SqliteTypeDAO implements TypeDAO {
    public Optional<Type> findOne(Integer key) {
        String sql = "SELECT * FROM type WHERE id = ?";
        Optional<Type> type = Optional.empty();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setInt(1, key);

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final String name = result.getString("name");

                type = Optional.of(Type.valueOf(name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return type;
    }

    public Optional<Integer> findId(Type entity) {
        String sql = "SELECT * FROM type WHERE name = ?";
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
