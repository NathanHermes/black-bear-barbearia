package br.ifsp.edu.blackbearbarbearia.domain.usecases.user;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;

import java.time.DayOfWeek;
import java.util.List;

public interface UserDayDAO {
    List<DayOfWeek> findByUserId(Integer userId);
    List<User> findByDayId(Integer dayId);
}
