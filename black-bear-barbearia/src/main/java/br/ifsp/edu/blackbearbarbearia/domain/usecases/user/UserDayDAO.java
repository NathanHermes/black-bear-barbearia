package br.ifsp.edu.blackbearbarbearia.domain.usecases.user;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;

import java.time.DayOfWeek;
import java.util.List;

public interface UserDayDAO {
    Boolean create(Integer userId, List<DayOfWeek> days);
    Boolean update(Integer userId, List<DayOfWeek> days);
    void deleteByUserId(Integer userId);
    List<DayOfWeek> findByUserId(Integer userId);
    List<User> findByDayId(Integer dayId);
}
