package br.ifsp.edu.blackbearbarbearia.application.repository.inmemory;

import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.ServiceBuilder;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.ServiceDAO;

import java.util.*;

public class InMemoryServiceDAO implements ServiceDAO {
    private static final Map<Integer, Service> database = new LinkedHashMap<>();
    private Integer serviceID = 0;

    @Override
    public Boolean create(Service service) {
        ServiceBuilder serviceBuilder = new ServiceBuilder();
        serviceID++;
        serviceBuilder.setId(serviceID);
        if (database.put(serviceID, service) != null)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }

    @Override
    public Boolean update(Service service) {
        database.replace(service.getId(), service);
        return true;
    }

    @Override
    public Boolean deleteByKey(Integer key) {return Boolean.FALSE;}

    @Override
    public Boolean delete(Service service) {
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
                .filter(service -> service.getName().equals(name))
                .findAny();
    }

    @Override
    public Optional<Integer> findKeyByName(String name) {
        return Optional.empty();
    }
}
