package br.ifsp.edu.blackbearbarbearia.application.repository;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDayDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.*;

public class SqliteUserDayDAO implements UserDayDAO {
    final SqliteDayDAO dayDAO = new SqliteDayDAO();

    @Override
    public void create(Integer userId, List<DayOfWeek> days) {
        String sql = """
                INSERT INTO userDay(
                    userId,
                    dayId
                ) VALUES (?, ?)
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            for(DayOfWeek day : days) {
                final Optional<Integer> dayId = dayDAO.findId(day);

                stmt.setInt(1, userId);
                stmt.setInt(2, dayId.get());

                stmt.executeUpdate();
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean update(Integer userId, List<DayOfWeek> days) {
        deleteByUserId(userId);
        create(userId, days);
        return findByUserId(userId).equals(days);
    }

    @Override
    public Boolean deleteByUserId(Integer userId) {
        String sql = """
                DELETE FROM userDay WHERE userId = ?
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return findByUserId(userId).isEmpty();
    }

    @Override
    public List<DayOfWeek> findByUserId(Integer userId) {
        String sql = "SELECT * FROM userDay WHERE userId = ?";
        List<DayOfWeek> days = new ArrayList<>();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setInt(1, userId);

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final Integer dayId = result.getInt("Dayid");

                DayOfWeek day = dayDAO.findOne(dayId).get();

                days.add(day);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return days;
    }

    @Override
    public List<User> findByDayId(Integer dayId) {
        return null;
    }
}
