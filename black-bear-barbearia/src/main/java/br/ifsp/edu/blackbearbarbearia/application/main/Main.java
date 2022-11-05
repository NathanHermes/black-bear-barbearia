package br.ifsp.edu.blackbearbarbearia.application.main;

import br.ifsp.edu.blackbearbarbearia.application.repository.inmemory.InMemoryUserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.LoginUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;

import java.util.Scanner;

public class Main {
    public static LoginUseCase loginUseCase;
    private static User user = null;
    public static void main(String[] args) {
        configureInjection();
        final Scanner input = new Scanner(System.in);
        if (user == null) {
            System.out.println("> Login:");
            String login = input.nextLine();
            System.out.println("> Password:");
            String password = input.nextLine();

            try {
                user = new User(login, password);
                System.out.println(loginUseCase.login(user));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
    private static void configureInjection() {
        UserDAO userDAO = new InMemoryUserDAO();
        loginUseCase = new LoginUseCase(userDAO);
    }
    private static void createAdmin() {
        User adm = new User(0, "Black Bear ADM", "blackbear@adm.com", "(16) 99999-9999", "Av. São Carlos", "2120", "", "SP", "São Carlos", "BBAdmin", "Admin123", true);

    }
}
