package br.ifsp.edu.blackbearbarbearia.application.repository;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;

import java.util.*;

public class InMemoryUserDAO implements UserDAO {
    private static final Map<Integer, User> database = new LinkedHashMap<>();
    private Integer userID = 0;
    @Override
    public Integer create(User user) {
        userID++;
        user.setId(userID);
        database.put(userID, user);
        return userID;
    }

    @Override
    public boolean update(User user) {
        Integer id = user.getId();
        if (!database.containsKey(id)) return false;

        database.replace(id, user);
        return true;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        if (!database.containsKey(key)) return false;

        database.remove(key);
        return true;
    }

    @Override
    public boolean delete(User user) {
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
    public Optional<User> findOneByLogin(String login) {
        return database.values().stream()
                .filter(user -> user.getLogin().equals(login))
                .findAny();
    }
}
