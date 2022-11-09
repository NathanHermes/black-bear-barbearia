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
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.TrocaSenhaUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;

import java.util.Scanner;

public class Main {
    public static LoginUseCase loginUseCase;
    public static CadastrarFuncionarioUseCase cadastrarFuncionarioUseCase;
    public static EditarFuncionarioUseCase editarFuncionarioUseCase;
    public static ListarFuncionariosUseCase listarFuncionariosUseCase;
    public static ApagarSenhaFuncionarioUseCase apagarSenhaFuncionarioUseCase;
    public static TrocaSenhaUseCase trocaSenhaUseCase;
    private static User user = null;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        configureInjection();
        createAdmin();

        login();

        Integer programOption;
        do {
            programOption = menu();

            switch (programOption) {
                case 0 -> System.out.println("FINISHED PROGRAM");
                case 1 -> {
                    System.out.println("\nAre you sure you want to logout?[Y/N]");
                    String confirmLogOut = scanner.nextLine().toUpperCase();

                    if (confirmLogOut.equals("Y")) {
                        System.out.println("\nBYE BYE, " + user.getLogin() + "\n");
                        user = null;
                        login();
                    }
                    else {
                        System.out.println("\nOK " + user.getLogin() + " ... NOT LOG-OUT XD\n");
                        programOption = -1;
                    }
                }
                case 2 -> {
                    try {
                        //createEmployee();
                        injectCreateEmployee();
                        System.out.println("\n> SUCCESS .....: Employee created\n");
                    }
                    catch (Exception e) {
                        System.out.println("\n> Error ...: " + e.getMessage());
                        System.out.println("> ERROR ...: User not created. Try again" + "\n");
                    }
                }
                case 3 -> {
                    try {
                        updateEmployee();
                        System.out.println("\n> SUCCESS .....: Employee created\n");
                    }
                    catch (Exception e) {
                        System.out.println("\n> Error ...: " + e.getMessage());
                        System.out.println("> ERROR ...: User not updated. Try again" + "\n");
                    }
                }
                case 4 -> {
                    try {
                        System.out.println("\n> EMPLOYEE");
                        listarFuncionariosUseCase.findAll().forEach(System.out::println);
                    } catch (Exception e) {
                        System.out.println("\n> Error ...: " + e.getMessage() + "\n");
                    }
                }
                case 5 -> {
                    try {
                        deleteEmployeePassword();
                        System.out.println("\n> SUCCESS .....: Password deleted\n");
                    } catch (Exception e) {
                        System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
                    }
                }
                case 6 -> {
                    try {
                        modifyEmployeePassword();
                        System.out.println("\nSUCCESS .....: Password modified\n");
                    } catch (Exception e) {
                        System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
                    }
                }
            }
        } while (programOption.compareTo(0) != 0);
    }

    public static Integer menu() {
        System.out.println("0. Exit program");
        System.out.println("1. Logout");
        System.out.println("---------------------------");
        System.out.println("2. Create employee");
        System.out.println("3. Update employee");
        System.out.println("4. List employees");
        System.out.println("5. Delete employee password");
        System.out.println("6. Modify employee password");
        System.out.println("---------------------------");
        System.out.println("7. Create client");
        System.out.println("Select an option:");
        return Integer.parseInt(scanner.nextLine());
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
        }
        catch (Exception e) {
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }
    private static void login() {
        do {
            System.out.println("> START WITH A LOGIN");
            System.out.println("> Login:");
            String login = scanner.nextLine();
            System.out.println("> Password:");
            String password = scanner.nextLine();

            try {
                user = loginUseCase.login(new User(login, password));
                if (user.getPasswordHash() == null) {
                    System.out.println("\n> WARNING ...: Your password is null");
                    System.out.println("> WARNING ...: You need a new password");
                }
                System.out.println("\n> SUCCESS .....: Log-in success\n");
            }
            catch (Exception e) {
                user = null;
                System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
            }
        } while (user == null);
    }

    private static void createEmployee() {
        if (user.hasRole(Role.ADMIN))
            throw new IllegalArgumentException("You are not an administrator.");

        System.out.println("\n> CREATE EMPLOYEE");
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

        Address newEmployeeAddress = new Address(address, number, complement, district, city);
        User newEmployee = new User(fullName, email, phone, newEmployeeAddress, login, password, Boolean.TRUE);
        newEmployee.addRole(Role.EMPLOYEE);

        int optionDay;
        do {
            optionDay = dayMenu();
            switch (optionDay) {
                case 0 -> optionDay = -1;
                case 1 -> newEmployee.addDay(Day.SUNDAY);
                case 2 -> newEmployee.addDay(Day.MONDAY);
                case 3 -> newEmployee.addDay(Day.TUESDAY);
                case 4 -> newEmployee.addDay(Day.WEDNESDAY);
                case 5 -> newEmployee.addDay(Day.THURSDAY);
                case 6 -> newEmployee.addDay(Day.FRIDAY);
                case 7 -> newEmployee.addDay(Day.SATURDAY);
                default -> System.out.println("> ERROR ...: Invalid option;");
            }
        } while (optionDay > 0 && newEmployee.getDays().size() < 6);

        cadastrarFuncionarioUseCase.create(newEmployee);
    }
    private static Integer dayMenu() {
        System.out.println("> WORK DAYS OPTIONS");
        System.out.println("> [0] Exit;");
        System.out.println("> [1] Sunday;");
        System.out.println("> [2] Monday;");
        System.out.println("> [3] Tuesday;");
        System.out.println("> [4] Wednesday;");
        System.out.println("> [5] Thursday;");
        System.out.println("> [6] Friday ;");
        System.out.println("> [7] Saturday ;");
        System.out.println("> Select a work day option:");
        return Integer.parseInt(scanner.nextLine());
    }

    private static void updateEmployee() {
        if (user.hasRole(Role.ADMIN))
            throw new IllegalArgumentException("You are not an administrator.");

        System.out.println("> Employee ID:");
        Integer id = Integer.valueOf(scanner.nextLine());
        if (listarFuncionariosUseCase.findOne(id).isEmpty())
            throw new EntityNotFoundException("User not found");

        System.out.println("\n> UPDATE EMPLOYEE");
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
        String inputActive = scanner.nextLine().toUpperCase();

        Boolean active = inputActive.equals("N") ? Boolean.FALSE : Boolean.TRUE;

        Address employeeUpdateAddress = new Address(address, number, complement, district, city);
        User employeeUpdate = new User(email, phone, employeeUpdateAddress, active);

        User employee = listarFuncionariosUseCase.findOne(id).get();
        editarFuncionarioUseCase.update(employee, employeeUpdate);
    }

    private static void deleteEmployeePassword() {
        if (user.hasRole(Role.ADMIN))
            throw new IllegalArgumentException("You are not an administrator.");

        System.out.println("> Employee ID:");
        Integer id = Integer.valueOf(scanner.nextLine());

        apagarSenhaFuncionarioUseCase.deletePassword(id);
    }

    private static void modifyEmployeePassword() {
        if (user.getPasswordHash() != null) throw new IllegalArgumentException("To change the password it needs to be deleted by the administrator");
        Boolean modify;
        do {
            System.out.println("> CREATE A NEW PASSWORD");
            System.out.println("> Last password ........:");
            String lastPassword = scanner.nextLine();
            System.out.println("> New password .........:");
            String newPassword = scanner.nextLine();
            System.out.println("> New password again ...:");
            String newPasswordConfirm = scanner.nextLine();

            modify = trocaSenhaUseCase.modifyPassword(user, lastPassword, newPassword, newPasswordConfirm);
        } while (modify.compareTo(Boolean.TRUE) != 0);
    }

    private static void createClient() {

    }
    private static void injectCreateEmployee() {
        Address e00Address = new Address("Rua Barão do Rio Branco", "0", "Sítio Paecara (Vicente de Carvalho)", "SP", "Guarujá");
        User e00 = new User("Grace Hopper", "grace.hopper@email.com", "(13) 2745-5802", e00Address, "Grace", "Hopper", Boolean.TRUE);
        e00.addRole(Role.EMPLOYEE);
        e00.addDay(Day.MONDAY);
        e00.addDay(Day.TUESDAY);
        e00.addDay(Day.WEDNESDAY);
        cadastrarFuncionarioUseCase.create(e00);

        Address e01Address = new Address("Rua Tenente Vítor Batista", "1", "Realengo", "RJ", "Rio de Janeiro");
        User e01 = new User("John Backus", "john.backus@email.com", "(24) 2208-8210", e01Address, "John", "Backus", Boolean.TRUE);
        e01.addRole(Role.EMPLOYEE);
        e01.addDay(Day.MONDAY);
        e01.addDay(Day.TUESDAY);
        e01.addDay(Day.THURSDAY);
        e01.addDay(Day.SATURDAY);
        cadastrarFuncionarioUseCase.create(e01);

        Address e02Address = new Address("Rua dos Crenaques", "2", "", "MG", "Belo Horizonte");
        User e02 = new User("Bill Gates", "gates.bill@email.com", "(33) 2544-6585", e02Address, "Gates", "Bill", Boolean.TRUE);
        e02.addRole(Role.EMPLOYEE);
        e02.addDay(Day.FRIDAY);
        cadastrarFuncionarioUseCase.create(e02);

        Address e03Address = new Address("Rua Suzano", "3", "", "PR", "Foz do Iguaçu");
        User e03 = new User("Brian Kernighan", "brian.kernighan@email.com", "(45) 2583-1359", e03Address, "Brian", "Brian", Boolean.TRUE);
        e03.addRole(Role.EMPLOYEE);
        e03.addDay(Day.THURSDAY);
        e03.addDay(Day.SATURDAY);
        cadastrarFuncionarioUseCase.create(e03);

        Address e04Address = new Address("Rua Isaura Magdalena", "4", "São Matheus", "MT", "Várzea Grande");
        User e04 = new User("Ken Thompson", "ken.thompson@email.com", "(65) 2642-6225", e04Address, "Thompson", "Ken", Boolean.TRUE);
        e04.addRole(Role.ADMIN);
        e04.addDay(Day.TUESDAY);
        e04.addDay(Day.WEDNESDAY);
        e04.addDay(Day.THURSDAY);
        e04.addDay(Day.SATURDAY);
        cadastrarFuncionarioUseCase.create(e04);
    }
}

