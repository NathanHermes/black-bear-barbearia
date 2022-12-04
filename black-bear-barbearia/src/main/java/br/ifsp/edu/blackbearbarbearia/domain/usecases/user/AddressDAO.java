package br.ifsp.edu.blackbearbarbearia.domain.usecases.user;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Address;

import java.util.Optional;

public interface AddressDAO {
    Integer create(Integer userId, Address type);
    Boolean update(Integer userId, Address type);
    Boolean deleteByKey(Integer key);
    Boolean deleteByUserId(Integer userId);
    Boolean delete(Address type);
    Optional<Address> findOne(Integer key);
    Optional<Address> findOneByUserId(Integer userId);
}
