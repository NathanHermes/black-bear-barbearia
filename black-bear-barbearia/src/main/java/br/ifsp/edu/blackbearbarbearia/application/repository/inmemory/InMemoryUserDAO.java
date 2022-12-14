package br.ifsp.edu.blackbearbarbearia.application.repository.inmemory;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;

import java.util.*;

public class InMemoryUserDAO implements UserDAO {
    private static final Map<Integer, User> database = new LinkedHashMap<>();
    private Integer userID = 0;
    @Override
    public Boolean create(User user) {
        userID++;
        //user.setId(userID);
        if (database.put(userID, user) != null)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }

    @Override
    public Boolean update(User user) {
        database.replace(user.getId(), user);
        return true;
    }

    @Override
    public Boolean deleteByKey(Integer key) {
        if (!database.containsKey(key)) return false;

        database.remove(key);
        return true;
    }

    @Override
    public Boolean delete(User user) {
        return deleteByKey(user.getId());
    }

    @Override
    public Optional<User> findOne(Integer key) {
        if (!database.containsKey(key)) return Optional.empty();

        return Optional.of(database.get(key));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return database.values().stream()
                .filter(user -> user.getLogin().equals(login))
                .findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return database.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
    }

    @Override
    public Integer findCountByDay(Integer day) {
        return database.values().stream()
                .filter(user -> user.getDays().contains(day))
                .toList().size();
    }

    @Override
    public Boolean updatePassword(String login, String passwordHash) {
        return null;
    }

    @Override
    public Optional<Integer> findIDByFullName(String fullName) {
        return Optional.empty();
    }
}
