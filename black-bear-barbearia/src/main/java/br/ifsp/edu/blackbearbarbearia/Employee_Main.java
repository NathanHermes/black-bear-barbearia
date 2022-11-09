package br.ifsp.edu.blackbearbarbearia;

import br.ifsp.edu.blackbearbarbearia.application.repository.InMemoryUserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Address;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Day;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Role;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.employee.CadastrarFuncionarioUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.employee.EditarFuncionarioUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.employee.ListarFuncionariosUseCase;


public class Employee_Main {
    public static void main(String[] args) {
        InMemoryUserDAO dao = new InMemoryUserDAO();
        Integer id;

        Address e00Address;
        User e00;

        Address e01Address;
        User e01;

        CadastrarFuncionarioUseCase cadastrarFuncionarioUseCase = new CadastrarFuncionarioUseCase(dao);
        EditarFuncionarioUseCase editarFuncionarioUseCase = new EditarFuncionarioUseCase(dao);
        ListarFuncionariosUseCase listarFuncionariosUseCase = new ListarFuncionariosUseCase(dao);

        // Cadastrar
        e00Address = new Address("Rua Barão do Rio Branco", "0", "Sítio Paecara (Vicente de Carvalho)", "SP", "Guarujá");
        e00 = new User("Grace Hopper", "grace.hopper@email.com", "(13) 2745-5802", e00Address, "Grace", "Hopper", Boolean.TRUE);
        e00.addRole(Role.EMPLOYEE);
        e00.addDay(Day.MONDAY);
        e00.addDay(Day.TUESDAY);
        e00.addDay(Day.WEDNESDAY);

        cadastrarFuncionarioUseCase = new CadastrarFuncionarioUseCase(dao);
        id = cadastrarFuncionarioUseCase.create(e00);
        System.out.println(dao.findOne(id));

        // Editar
        e00Address = new Address("Rua Barão do Rio Branco", "0", "Sítio Paecara (Vicente de Carvalho)", "SP", "Guarujá");
        e00 = new User("Grace Hopper", "grace.hopper@email.com", "(13) 2745-5802", e00Address, "Grace", "Hopper", Boolean.TRUE);
        e00.addRole(Role.EMPLOYEE);
        e00.addDay(Day.MONDAY);
        e00.addDay(Day.TUESDAY);
        e00.addDay(Day.WEDNESDAY);

        cadastrarFuncionarioUseCase = new CadastrarFuncionarioUseCase(dao);
        id = cadastrarFuncionarioUseCase.create(e00);
        System.out.println(dao.findOne(id));

        e01Address = new Address("Rua Tenente Vítor Batista", "1", "Realengo", "RJ", "Rio de Janeiro");
        e01 = new User("John Backus", "john.backus@email.com", "(24) 2208-8210", e01Address, "John", "Backus", Boolean.TRUE);
        e01.addRole(Role.EMPLOYEE);
        e01.addDay(Day.MONDAY);
        e01.addDay(Day.TUESDAY);
        e01.addDay(Day.THURSDAY);
        e01.addDay(Day.SATURDAY);

        System.out.println(dao.findOne(id));
        //editarFuncionarioUseCase.update(e00, e01);
        System.out.println(dao.findOne(id));

        // Listar

        System.out.println(dao.findAll());
    }
}
