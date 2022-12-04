package br.ifsp.edu.blackbearbarbearia.domain.usecases.user;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Address;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.DAO;

import java.util.Optional;

public interface AddressDAO extends DAO<Address, Integer> {
    Optional<Address> findOneByUserId(Integer userId);
}
