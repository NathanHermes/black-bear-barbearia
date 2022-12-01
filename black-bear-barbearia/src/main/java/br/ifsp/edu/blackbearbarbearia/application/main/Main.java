package br.ifsp.edu.blackbearbarbearia.application.main;

import br.ifsp.edu.blackbearbarbearia.application.repository.InMemoryUserDAO;
import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Address;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Role;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.UserBuilder;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.employee.CadastrarFuncionarioUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.LoginUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class Main {
    public static LoginUseCase loginUseCase;
    public static CadastrarFuncionarioUseCase cadastrarFuncionarioUseCase;
    public static User USER;

    public static void main(String[] args) {
        configureInjection();
        createAdmin();
        WindowLoader.main(args);
    }

    private static void createAdmin() {
        Address admAddress = new Address("Av. São Carlos", "2120", "", "SP", "São Carlos");
        ArrayList<DayOfWeek> workDaysOfAdm = new ArrayList<>();
        workDaysOfAdm.add(DayOfWeek.MONDAY);
        workDaysOfAdm.add(DayOfWeek.TUESDAY);
        workDaysOfAdm.add(DayOfWeek.WEDNESDAY);
        workDaysOfAdm.add(DayOfWeek.THURSDAY);

        UserBuilder builder = new UserBuilder();
        builder.setFullName("Black Bear ADM");
        builder.setEmail("blackbear@adm.com");
        builder.setPhone("(16) 98135-7876");
        builder.setAddress(admAddress);
        builder.setLogin("BB");
        builder.setPasswordHash("123");
        builder.setActive(Boolean.TRUE);
        builder.setRole(Role.ADMIN);
        builder.setDays(workDaysOfAdm);

        User adm = builder.getResult();

        try {
            cadastrarFuncionarioUseCase.create(adm);
            System.out.println("> SUCCESS .....: Administrator created");
        }
        catch (Exception e) {
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }

    private static void configureInjection() {
        UserDAO userDAO = new InMemoryUserDAO();
        loginUseCase = new LoginUseCase(userDAO);
        cadastrarFuncionarioUseCase = new CadastrarFuncionarioUseCase(userDAO);
    }
}
