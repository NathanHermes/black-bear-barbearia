package br.ifsp.edu.blackbearbarbearia.application.main;

import br.ifsp.edu.blackbearbarbearia.application.repository.InMemoryUserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Day;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Role;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.employee.CadastrarFuncionarioUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.employee.ListarFuncionariosUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.LoginUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;

import java.nio.charset.IllegalCharsetNameException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static LoginUseCase loginUseCase;
    public static CadastrarFuncionarioUseCase cadastrarFuncionarioUseCase;

    public static ListarFuncionariosUseCase listarFuncionariosUseCase;
    private static User user = null;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        configureInjection();
        createAdmin();

        Main main = new Main();
        int opcao = -1;

        main.login();

        while (opcao != 0) {
            opcao = main.menu();
            switch (opcao) {
                case 1 -> {
                    try { main.createEmployee(); }
                    catch (Exception e) { System.out.println("\n> Error ...: " + e.getMessage() + "\n"); }
                }
                case 2 -> updateEmployee();
                case 3 -> listEmployee();
            }
        }
    }

    public int menu() {
        System.out.println("0. Sair");
        System.out.println("1. Create employee");
        System.out.println("2. Update employee");
        System.out.println("3. List employees");
        System.out.println("Escolha: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private static void configureInjection() {
        UserDAO userDAO = new InMemoryUserDAO();
        cadastrarFuncionarioUseCase = new CadastrarFuncionarioUseCase(userDAO);
        loginUseCase = new LoginUseCase(userDAO);
        listarFuncionariosUseCase = new ListarFuncionariosUseCase(userDAO);
    }
    private static void createAdmin() {
        User adm = new User("Black Bear ADM", "blackbear@adm.com", "(16) 99999-9999", "Av. São Carlos", "2120", "", "SP", "São Carlos", "BBAdmin", "Admin123", true);
        adm.addRole(Role.ADMIN);
        adm.addDay(Day.MONDAY);
        adm.addDay(Day.TUESDAY);
        adm.addDay(Day.WEDNESDAY);
        adm.addDay(Day.THURSDAY);

        try {
            cadastrarFuncionarioUseCase.create(adm);
        } catch (Exception e) {
            System.err.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }

    private void login() {
        while (user == null) {
            System.out.println("> PARA ACESSAR FAÇA O LOGIN");
            System.out.println("> Login:");
            String login = scanner.nextLine();
            System.out.println("> Password:");
            String password = scanner.nextLine();

            try {
                user = loginUseCase.login(new User(login, password));
                System.out.println("\n> SUCESSO .....: Login Efetuado.\n");
            } catch (Exception e) {
                user = null;
                System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
            }
        }
    }

    private void createEmployee() {
        if (!user.hasRole(Role.ADMIN))
            throw new IllegalArgumentException("You are not an administrator.");

        System.out.println("> CREATE EMPLOYEE");
        System.out.println("> Full name ...:");
        String fullName = scanner.nextLine();
        System.out.println("> Email .......:");
        String email = scanner.nextLine();
        System.out.println("> Phone .......:");
        String phone = scanner.nextLine();
        System.out.println("> Address .....:");
        String address = scanner.nextLine();
        System.out.println("> Number ......:");
        String number = scanner.nextLine();
        System.out.println("> Complement ..:");
        String complement = scanner.nextLine();
        System.out.println("> District ....:");
        String district = scanner.nextLine();
        System.out.println("> City ........:");
        String city = scanner.nextLine();
        System.out.println("> Login .......:");
        String login = scanner.nextLine();
        System.out.println("> Password ....:");
        String password = scanner.nextLine();

        User newEmployee = new User(fullName, email, phone, address, number, complement, district, city, login, password, true);

        int optionRole = 0;
        while (optionRole == 0) {
            optionRole = roleMenu();
            switch (optionRole) {
                case 1 -> newEmployee.addRole(Role.ADMIN);
                case 2 -> newEmployee.addRole(Role.EMPLOYEE);
                case 3 -> {
                    newEmployee.addRole(Role.ADMIN);
                    newEmployee.addRole(Role.EMPLOYEE);
                }
                default -> {
                    optionRole = 0;
                    System.out.println("> ERROR ...: Invalid option;");
                }
            }
        }

        int optionDay = -1;
        int countDays = 0;
        while (optionDay != 0 && countDays < 6) {
            optionDay = dayMenu();
            switch (optionDay) {
                case 0 -> System.out.println("> SUCCESS .....: Added days.");
                case 1 -> newEmployee.addDay(Day.SUNDAY);
                case 2 -> newEmployee.addDay(Day.MONDAY);
                case 3 -> newEmployee.addDay(Day.TUESDAY);
                case 4 -> newEmployee.addDay(Day.WEDNESDAY);
                case 5 -> newEmployee.addDay(Day.THURSDAY);
                case 6 -> newEmployee.addDay(Day.FRIDAY);
                case 7 -> newEmployee.addDay(Day.SATURDAY);
                default -> System.out.println("> ERROR ...: Invalid option;");
            }
            countDays++;
        }

        try { cadastrarFuncionarioUseCase.create(newEmployee); }
        catch (Exception e) { System.err.println("\n> ERROR ...: " + e.getMessage() + "\n"); }
    }

    private Integer roleMenu() {
        System.out.println("> ROLES OPTIONS");
        System.out.println("> [1] Admin;");
        System.out.println("> [2] Employee;");
        System.out.println("> [3] Admin and Employee ;");
        System.out.println("> Select an option:");
        return Integer.parseInt(scanner.nextLine());
    }

    private Integer dayMenu() {
        System.out.println("> WORK DAYS OPTIONS");
        System.out.println("> [0] Exit;");
        System.out.println("> [1] Sunday;");
        System.out.println("> [2] Monday;");
        System.out.println("> [3] Tuesday;");
        System.out.println("> [4] Wednesday;");
        System.out.println("> [5] Thursday;");
        System.out.println("> [6] Friday ;");
        System.out.println("> [7] Saturday ;");
        System.out.println("> Select an option work day:");
        return Integer.parseInt(scanner.nextLine());
    }
    private static void updateEmployee() {
        if (!user.hasRole(Role.ADMIN))
            throw new IllegalArgumentException("You are not an administrator.");

        System.out.println("> EMPLOYEES");
        listEmployee();

        System.out.println("> Inform the ID: ");
        Integer id = Integer.valueOf(scanner.nextLine());

        System.out.println("> UPDATE EMPLOYEE");

        System.out.println("> Email .......:");
        String email = scanner.nextLine();
        System.out.println("> Phone .......:");
        String phone = scanner.nextLine();
        System.out.println("> Address .....:");
        String address = scanner.nextLine();
        System.out.println("> Number ......:");
        String number = scanner.nextLine();
        System.out.println("> Complement ..:");
        String complement = scanner.nextLine();
        System.out.println("> District ....:");
        String district = scanner.nextLine();
        System.out.println("> City ........:");
        String city = scanner.nextLine();
        System.out.println("> Active [Y/N].:");
        Boolean active = Boolean.valueOf(scanner.nextLine());
    }

    private static void listEmployee() {
        listarFuncionariosUseCase.findAll().forEach(System.out::println);
    }
}

