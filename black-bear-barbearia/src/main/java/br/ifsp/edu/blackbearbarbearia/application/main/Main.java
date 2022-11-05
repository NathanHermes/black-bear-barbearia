package br.ifsp.edu.blackbearbarbearia.application.main;

import br.ifsp.edu.blackbearbarbearia.application.repository.inmemory.InMemoryUserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Day;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Role;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.employee.CadastrarFuncionarioUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.LoginUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;

import java.util.Scanner;

public class Main {
    public static LoginUseCase loginUseCase;
    public static CadastrarFuncionarioUseCase cadastrarFuncionarioUseCase;
    private static User user = null;
    public static void main(String[] args) {
        configureInjection();
        createAdmin();

        final Scanner input = new Scanner(System.in);

        while (user == null) {
            System.out.println("> Login:");
            String login = input.nextLine();
            System.out.println("> Password:");
            String password = input.nextLine();

            try {
                user = new User(login, password);
                if (loginUseCase.login(user) != null) System.out.println("[   LOGIN EFETUADO   ]");
            } catch (Exception e) {
                user = null;
                System.out.println(e.getMessage() + "\n[... TENTE NOVAMENTE ...]\n");
            }
        }
    }
    private static void configureInjection() {
        UserDAO userDAO = new InMemoryUserDAO();
        cadastrarFuncionarioUseCase = new CadastrarFuncionarioUseCase(userDAO);
        loginUseCase = new LoginUseCase(userDAO);
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
            System.err.println(e.getMessage());
        }

    }
}
