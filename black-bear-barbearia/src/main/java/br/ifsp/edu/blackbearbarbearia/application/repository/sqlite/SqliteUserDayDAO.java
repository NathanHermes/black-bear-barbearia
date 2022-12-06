package br.ifsp.edu.blackbearbarbearia.application.repository.sqlite;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDayDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.*;

public class SqliteUserDayDAO implements UserDayDAO {
    final SqliteDayDAO dayDAO = new SqliteDayDAO();

    @Override
    public Boolean create(Integer userId, List<DayOfWeek> days) {
        String sql = """
                INSERT INTO userDay(
                    userId,
                    dayId
                ) VALUES (?, ?)
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            for(DayOfWeek day : days) {
                if (dayDAO.findDayIDByDay(day).isEmpty())
                    throw new EntityNotFoundException("Day not found");
                final Integer dayId = dayDAO.findDayIDByDay(day).get();

                stmt.setInt(1, userId);
                stmt.setInt(2, dayId);

                stmt.executeUpdate();
            }
            return Boolean.TRUE;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean update(Integer userId, List<DayOfWeek> days) {
        deleteByUserId(userId);
        return create(userId, days);
    }

    @Override
    public void deleteByUserId(Integer userId) {
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
