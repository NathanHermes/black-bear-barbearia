package br.ifsp.edu.blackbearbarbearia.application.repository;

import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.client.ClientDAO;

import java.util.*;

public class InMemoryClientDAO implements ClientDAO {
    private static final Map<Integer, Client> database = new LinkedHashMap<>();
    private Integer clientID = 0;

    @Override
    public Integer create(Client client) {
        clientID++;
        client.setId(clientID);
        database.put(clientID, client);
        return clientID;
    }

    @Override
    public boolean update(Client client) {
        Integer id = client.getId();
        if (!database.containsKey(id)) return false;

        database.replace(id, client);
        return true;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        if (!database.containsKey(key)) return false;

        database.remove(key);
        return true;
    }

    @Override
    public boolean delete(Client client) {
        return deleteByKey(client.getId());
    }

    @Override
    public Optional<Client> findOne(Integer key) {
        if (!database.containsKey(key)) return Optional.empty();

        return Optional.of(database.get(key));
    }

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Optional<Client> findOneByEmail(String email) {
        return database.values().stream()
                .filter(client -> client.getEmail().equals(email))
                .findAny();
    }
}
