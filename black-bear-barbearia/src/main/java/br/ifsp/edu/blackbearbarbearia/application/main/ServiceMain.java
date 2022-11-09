package br.ifsp.edu.blackbearbarbearia.application.main;


import br.ifsp.edu.blackbearbarbearia.application.repository.InMemoryServiceDAO;
import br.ifsp.edu.blackbearbarbearia.application.repository.InMemoryUserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Type;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Address;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Day;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Role;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.employee.CadastrarFuncionarioUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.CadastrarServicosUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.EditarServicoUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.ListarServicosUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.ServiceDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.LoginUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;

import java.math.BigDecimal;

public class ServiceMain {
    public static LoginUseCase loginUseCase;
    public static CadastrarFuncionarioUseCase cadastrarFuncionarioUseCase;
    public static CadastrarServicosUseCase cadastrarServicosUseCase;
    public static EditarServicoUseCase editarServicoUseCase;
    public static ListarServicosUseCase listarServicosUseCase;

    private static User user = null;

    public static void main(String[] args) {
        configureInjection();
        createAdmin();
        login(new User("BB", "123"));

        injectCreateService();
        findServices();
        updateService();
        findService();
    }


    private static void configureInjection() {
        UserDAO userDAO = new InMemoryUserDAO();
        loginUseCase = new LoginUseCase(userDAO);
        cadastrarFuncionarioUseCase = new CadastrarFuncionarioUseCase(userDAO);

        ServiceDAO serviceDAO = new InMemoryServiceDAO();
        cadastrarServicosUseCase = new CadastrarServicosUseCase(serviceDAO);
        editarServicoUseCase = new EditarServicoUseCase(serviceDAO);
        listarServicosUseCase = new ListarServicosUseCase(serviceDAO);
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

    private static void injectCreateService() {
        Service s0 = new Service("Corte de cabelo e barba", new BigDecimal("45.0"), new BigDecimal("0.48"), new BigDecimal("0.1"), Boolean.TRUE);
        s0.addType(Type.HAIR);
        s0.addType(Type.BEARD);

        Service s1 = new Service("Corte de cabelo", new BigDecimal("30.0"), new BigDecimal("0.4"), new BigDecimal("0.08"), Boolean.TRUE);
        s1.addType(Type.HAIR);

        Service s2 = new Service("Corte de barba", new BigDecimal("25.0"), new BigDecimal("0.4"), new BigDecimal("0.08"), Boolean.TRUE);
        s2.addType(Type.HAIR);

        Service s3 = new Service("Risquinho de cria", new BigDecimal("15.0"), new BigDecimal("0.35"), new BigDecimal("0.05"), Boolean.TRUE);
        s3.addType(Type.OTHER);

        try {
            cadastrarServicosUseCase.create(s0);
            System.out.println("> SUCCESS .....: Service created");
            cadastrarServicosUseCase.create(s1);
            System.out.println("> SUCCESS .....: Service created");
            cadastrarServicosUseCase.create(s2);
            System.out.println("> SUCCESS .....: Service created");
            cadastrarServicosUseCase.create(s3);
            System.out.println("> SUCCESS .....: Service created");
        } catch (Exception e) {
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }

    private static void updateService() {
        if (user.hasRole(Role.ADMIN))
            throw new IllegalArgumentException("You are not an administrator.");

        Service serviceUpdate = new Service(new BigDecimal("0.4"), new BigDecimal("0.08"), Boolean.TRUE);
        serviceUpdate.addType(Type.BEARD);

        try {
            editarServicoUseCase.update(4, serviceUpdate);
            System.out.println("> SUCCESS .....: Service updated");
        } catch (Exception e) {
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }

    private static void findServices() {
        System.out.println("\n> REGISTERED SERVICES");

        try {
            listarServicosUseCase.findAll().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }

    private static void findService() {
        try {
            System.out.println(listarServicosUseCase.findOne(1));
        } catch (Exception e) {
            System.out.println("> ERROR ...: " + e.getMessage() + "\n");
        }
    }
}
