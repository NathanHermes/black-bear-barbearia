package br.ifsp.edu.blackbearbarbearia.application.repository;

import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.ServiceDAO;

import java.util.*;

public class inMemoryServiceDao implements ServiceDAO {
    private static final Map<Integer, Service> database = new LinkedHashMap<>();
    private static Integer serviceID;

    @Override
    public Integer create(Service service) {
        serviceID++;
        service.setId(serviceID);
        database.put(serviceID, service);
        return serviceID;
    }

    @Override
    public boolean update(Service service) {
        Integer id = service.getId();
        if(!database.containsKey(id)) return false;

        database.replace(id, service);
        return true;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        if (!database.containsKey(key)) return false;

        database.remove(key);
        return true;
    }

    @Override
    public boolean delete(Service service) {
        return deleteByKey(service.getId());
    }

    @Override
    public Optional<Service> findOne(Integer key) {
        if (!database.containsKey(key)) return Optional.empty();

        return Optional.of(database.get(key));
    }

    @Override
    public List<Service> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Optional<Service> findOneByName(String name) {
        return database.values().stream()
                .filter(service -> service.getNome().equals(name))
                .findAny();
    }
}
