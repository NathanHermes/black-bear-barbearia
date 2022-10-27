package br.edu.ifsp.domain.usecase.employee;

import br.edu.ifsp.domain.entities.user.User;
import br.edu.ifsp.domain.usecase.utils.Dao;

public interface EmployeeDao extends Dao<User, Integer> {}
