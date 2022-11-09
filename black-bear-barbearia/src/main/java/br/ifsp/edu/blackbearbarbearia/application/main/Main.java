package br.ifsp.edu.blackbearbarbearia.application.main;

import br.ifsp.edu.blackbearbarbearia.application.repository.InMemoryUserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Address;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Day;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Role;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.employee.CadastrarFuncionarioUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.employee.EditarFuncionarioUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.employee.ListarFuncionariosUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.ApagarSenhaFuncionarioUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.LoginUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.employee.TrocaSenhaUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;

import java.util.Scanner;

public class Main {
    public static LoginUseCase loginUseCase;
    public static CadastrarFuncionarioUseCase cadastrarFuncionarioUseCase;
    public static EditarFuncionarioUseCase editarFuncionarioUseCase;
    public static ListarFuncionariosUseCase listarFuncionariosUseCase;
    public static ApagarSenhaFuncionarioUseCase apagarSenhaFuncionarioUseCase;
    public static TrocaSenhaUseCase trocaSenhaUseCase;
    private static User user = null;

    public static void main(String[] args) {
        configureInjection();
        createAdmin();
        login(new User("BB", "123"));

        injectCreateEmployee();
        updateEmployee();

        modifyEmployeePassword();
        deleteEmployeePassword();

        login(new User("Grace", "Hopper"));
        modifyEmployeePassword();
    }


    private static void configureInjection() {
        UserDAO userDAO = new InMemoryUserDAO();
        cadastrarFuncionarioUseCase = new CadastrarFuncionarioUseCase(userDAO);
        loginUseCase = new LoginUseCase(userDAO);
        listarFuncionariosUseCase = new ListarFuncionariosUseCase(userDAO);
        editarFuncionarioUseCase = new EditarFuncionarioUseCase(userDAO);
        apagarSenhaFuncionarioUseCase = new ApagarSenhaFuncionarioUseCase(userDAO);
        trocaSenhaUseCase = new TrocaSenhaUseCase(userDAO);
    }

    private static void createAdmin() {
        Address admAddress = new Address("Av. São Carlos", "2120", "", "SP", "São Carlos");
        User adm = new User("Black Bear ADM", "blackbear@adm.com", "(16) 99999-9999", admAddress, "BB", "123", Boolean.TRUE);
        adm.addRole(Role.ADMIN);
        adm.addDay(Day.MONDAY);
        adm.addDay(Day.TUESDAY);
        adm.addDay(Day.WEDNESDAY);
        adm.addDay(Day.THURSDAY);

        try {
            cadastrarFuncionarioUseCase.create(adm);
            System.out.println("> SUCCESS .....: Administrator created");
        }
        catch (Exception e) {
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }

    private static void login(User login) {
        try {
            user = loginUseCase.login(login);
            System.out.println("> SUCCESS .....: Log-in success");
        }
        catch (Exception e) {
            user = null;
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }

    private static void injectCreateEmployee() {
        Address e00Address = new Address("Rua Barão do Rio Branco", "0", "Sítio Paecara (Vicente de Carvalho)", "SP", "Guarujá");
        User e00 = new User("Grace Hopper", "grace.hopper@email.com", "(13) 2745-5802", e00Address, "Grace", "Hopper", Boolean.TRUE);
        e00.addRole(Role.EMPLOYEE);
        e00.addDay(Day.MONDAY);
        e00.addDay(Day.TUESDAY);
        e00.addDay(Day.WEDNESDAY);

        Address e01Address = new Address("Rua Tenente Vítor Batista", "1", "Realengo", "RJ", "Rio de Janeiro");
        User e01 = new User("John Backus", "john.backus@email.com", "(24) 2208-8210", e01Address, "John", "Backus", Boolean.TRUE);
        e01.addRole(Role.EMPLOYEE);
        e01.addDay(Day.MONDAY);
        e01.addDay(Day.TUESDAY);
        e01.addDay(Day.THURSDAY);
        e01.addDay(Day.SATURDAY);

        Address e02Address = new Address("Rua dos Crenaques", "2", "", "MG", "Belo Horizonte");
        User e02 = new User("Bill Gates", "gates.bill@email.com", "(33) 2544-6585", e02Address, "Gates", "Bill", Boolean.TRUE);
        e02.addRole(Role.EMPLOYEE);
        e02.addDay(Day.FRIDAY);

        Address e03Address = new Address("Rua Suzano", "3", "", "PR", "Foz do Iguaçu");
        User e03 = new User("Brian Kernighan", "brian.kernighan@email.com", "(45) 2583-1359", e03Address, "Brian", "Brian", Boolean.TRUE);
        e03.addRole(Role.EMPLOYEE);
        e03.addDay(Day.THURSDAY);
        e03.addDay(Day.SATURDAY);

        Address e04Address = new Address("Rua Isaura Magdalena", "4", "São Matheus", "MT", "Várzea Grande");
        User e04 = new User("Ken Thompson", "ken.thompson@email.com", "(65) 2642-6225", e04Address, "Thompson", "Ken", Boolean.TRUE);
        e04.addRole(Role.ADMIN);
        e04.addDay(Day.TUESDAY);
        e04.addDay(Day.WEDNESDAY);
        e04.addDay(Day.THURSDAY);
        e04.addDay(Day.SATURDAY);

        try {
            cadastrarFuncionarioUseCase.create(e00);
            System.out.println("> SUCCESS .....: Employee created");
            cadastrarFuncionarioUseCase.create(e01);
            System.out.println("> SUCCESS .....: Employee created");
            cadastrarFuncionarioUseCase.create(e02);
            System.out.println("> SUCCESS .....: Employee created");
            cadastrarFuncionarioUseCase.create(e03);
            System.out.println("> SUCCESS .....: Employee created");
            cadastrarFuncionarioUseCase.create(e04);
            System.out.println("> SUCCESS .....: Employee created");
        } catch (Exception e) {
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }

    private static void updateEmployee() {
        if (user.hasRole(Role.ADMIN))
            throw new IllegalArgumentException("You are not an administrator.");

        Address employeeUpdateAddress = new Address("Rua dos Crenaques", "40", "Venda nova", "MG", "Belo Horizonte");
        User employeeUpdate = new User("bill.gates@email.com", "(65) 2642-6225", employeeUpdateAddress, Boolean.FALSE);

        try {
            editarFuncionarioUseCase.update(4, employeeUpdate);
            System.out.println("> SUCCESS .....: Employee updated");
        } catch (Exception e) {
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }

    private static void deleteEmployeePassword() {
        if (user.hasRole(Role.ADMIN))
            throw new IllegalArgumentException("You are not an administrator.");

        try{
            apagarSenhaFuncionarioUseCase.deletePassword(2);
            System.out.println("> SUCCESS .....: Password is deleted");
        } catch (Exception e) {
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }

    private static void modifyEmployeePassword() {
        try {
            trocaSenhaUseCase.modifyPassword(user, "Hopper", "Grace", "Grace");
            System.out.println("> SUCCESS .....: Password is modified");
        } catch (Exception e) {
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }
}

