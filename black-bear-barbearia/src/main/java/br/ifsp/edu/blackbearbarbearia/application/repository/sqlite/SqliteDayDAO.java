package br.ifsp.edu.blackbearbarbearia.application.repository.sqlite;

import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.DayDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.Optional;

public class SqliteDayDAO implements DayDAO {
    @Override
    public Optional<DayOfWeek> findOne(Integer dayId) {
        String sql = "SELECT * FROM day WHERE id = ?";

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            stmt.setInt(1, dayId);

            final ResultSet result = stmt.executeQuery();

            if(result.next()){
                final String name = result.getString("name");
                return Optional.of(DayOfWeek.valueOf(name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Integer> findDayIDByDay(DayOfWeek day) {
        String sql = "SELECT * FROM day WHERE name = ?";

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            stmt.setString(1, day.name());

            final ResultSet result = stmt.executeQuery();
            if(result.next())
                return Optional.of(result.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
