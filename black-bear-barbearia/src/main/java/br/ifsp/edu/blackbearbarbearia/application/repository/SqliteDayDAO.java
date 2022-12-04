package br.ifsp.edu.blackbearbarbearia.application.repository;

import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.DayDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.Optional;

public class SqliteDayDAO implements DayDAO {
    @Override
    public Optional<DayOfWeek> findOne(Integer key) {
        String sql = "SELECT * FROM day WHERE id = ?";
        Optional<DayOfWeek> dayOfWeek = Optional.empty();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setInt(1, key);

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final String name = result.getString("name");

                dayOfWeek = Optional.of(DayOfWeek.valueOf(name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dayOfWeek;
    }

    @Override
    public Optional<Integer> findId(DayOfWeek entity) {
        String sql = "SELECT * FROM day WHERE name = ?";
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
