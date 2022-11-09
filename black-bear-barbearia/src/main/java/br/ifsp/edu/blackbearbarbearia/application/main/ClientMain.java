package br.ifsp.edu.blackbearbarbearia.application.main;

import br.ifsp.edu.blackbearbarbearia.application.repository.InMemoryClientDAO;
import br.ifsp.edu.blackbearbarbearia.application.repository.InMemoryUserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Address;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Day;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Role;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.client.CadastrarClienteUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.client.ClientDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.client.EditarClienteUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.client.ListarClientesUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.employee.CadastrarFuncionarioUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.LoginUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;

public class ClientMain {
    public static CadastrarClienteUseCase cadastrarClienteUseCase;
    public static EditarClienteUseCase editarClienteUseCase;
    public static ListarClientesUseCase listarClientesUseCase;
    public static LoginUseCase loginUseCase;
    public static CadastrarFuncionarioUseCase cadastrarFuncionarioUseCase;

    private static User user = null;

    public static void main(String[] args) {
        configureInjection();
        createAdmin();
        login(new User("BB", "123"));

        injectCreateClient();
        listClients();
        updateClient();
        listClients();
    }

    private static void configureInjection() {
        UserDAO userDAO = new InMemoryUserDAO();
        cadastrarFuncionarioUseCase = new CadastrarFuncionarioUseCase(userDAO);
        loginUseCase = new LoginUseCase(userDAO);

        ClientDAO clientDAO = new InMemoryClientDAO();
        cadastrarClienteUseCase = new CadastrarClienteUseCase(clientDAO);
        listarClientesUseCase = new ListarClientesUseCase(clientDAO);
        editarClienteUseCase = new EditarClienteUseCase(clientDAO);

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

    private static void injectCreateClient() {
        Client client1 = new Client("Patrícia Celestino Montilla", "patricia.montilla@gmail.com", "(89) 99963-7882");

        Client client2 = new Client("Lucimberto Cristo Leonicio", "lucimberto.leonicio@gmail.com.br", "(83) 96938-6221");

        Client client3 = new Client("Marcele Amaral Thomaz", "marcele.thomaz@gmail.com.br", "(88) 98326-2818");

        Client client4 = new Client("Wellington Vasgestian Quintela", "wellington.quintela@gmail.com.br", "(16) 97927-9672");

        Client client5 = new Client("Ataíde Guerini Facre", "ataide.facre@gmail.com.br", "(92) 96876-2986");


        try {
            cadastrarClienteUseCase.create(client1);
            System.out.println("> SUCCESS .....: Client created");
            cadastrarClienteUseCase.create(client2);
            System.out.println("> SUCCESS .....: Client created");
            cadastrarClienteUseCase.create(client3);
            System.out.println("> SUCCESS .....: Client created");
            cadastrarClienteUseCase.create(client4);
            System.out.println("> SUCCESS .....: Client created");
            cadastrarClienteUseCase.create(client5);
            System.out.println("> SUCCESS .....: Client created");
        } catch (Exception e) {
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }

    private static void updateClient() {
        Client clientUpdate = new Client("Patrícia Celestino Montilla", "patricia.montilla@gmail.com", "(89) 99999-9999");

        try {
            editarClienteUseCase.update(1, clientUpdate);
            System.out.println("> SUCCESS .....: Client updated");
            System.out.println();
        } catch (Exception e) {
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }

    private static void listClients () {
        System.out.println("> Registered clients");

        try {
            listarClientesUseCase.findAll().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }
}
