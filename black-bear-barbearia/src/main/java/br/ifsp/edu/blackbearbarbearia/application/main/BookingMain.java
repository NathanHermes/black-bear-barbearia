package br.ifsp.edu.blackbearbarbearia.application.main;

import br.ifsp.edu.blackbearbarbearia.application.repository.InMemoryBookingDAO;
import br.ifsp.edu.blackbearbarbearia.application.repository.InMemoryClientDAO;
import br.ifsp.edu.blackbearbarbearia.application.repository.InMemoryServiceDAO;
import br.ifsp.edu.blackbearbarbearia.application.repository.InMemoryUserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Type;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Address;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Day;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Role;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.booking.*;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.client.CadastrarClienteUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.client.ClientDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.client.ListarClientesUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.employee.CadastrarFuncionarioUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.employee.ListarFuncionariosUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.CadastrarServicosUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.ListarServicosUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.ServiceDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.LoginUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class BookingMain {
    public static LoginUseCase loginUseCase;
    public static CadastrarFuncionarioUseCase cadastrarFuncionarioUseCase;
    public static CadastrarClienteUseCase cadastrarClienteUseCase;
    public static CadastrarServicosUseCase cadastrarServicosUseCase;
    public static CriarAgendamentoUseCase criarAgendamentoUseCase;
    public static ListarClientesUseCase listarClientesUseCase;
    public static ListarServicosUseCase listarServicosUseCase;
    public static ListarFuncionariosUseCase listarFuncionariosUseCase;
    public static ConcluirAgendamentoUseCase concluirAgendamentoUseCase;
    public static CancelarAgendamentoUseCase cancelarAgendamentoUseCase;
    public static FiltrarFuncionariosAgendadosPorDiaUseCase filtrarFuncionariosAgendadosPorDiaUseCase;

    private static User user = null;

    public static void main(String[] args) {
        configureInjection();
        createUser();
        login(new User("BB", "123"));

        injectCreateClient();
        injectCreateService();
        injectCreateBooking();
        completeBooking();
        canceledBooking();
        filterBookingByDay();
    }

    private static void configureInjection() {
        UserDAO userDAO = new InMemoryUserDAO();
        cadastrarFuncionarioUseCase = new CadastrarFuncionarioUseCase(userDAO);
        listarFuncionariosUseCase = new ListarFuncionariosUseCase(userDAO);
        loginUseCase = new LoginUseCase(userDAO);

        ClientDAO clientDAO = new InMemoryClientDAO();
        cadastrarClienteUseCase = new CadastrarClienteUseCase(clientDAO);
        listarClientesUseCase = new ListarClientesUseCase(clientDAO);

        ServiceDAO serviceDAO = new InMemoryServiceDAO();
        cadastrarServicosUseCase = new CadastrarServicosUseCase(serviceDAO);
        listarServicosUseCase = new ListarServicosUseCase(serviceDAO);

        BookingDAO bookingDAO = new InMemoryBookingDAO();
        criarAgendamentoUseCase = new CriarAgendamentoUseCase(bookingDAO);
        concluirAgendamentoUseCase = new ConcluirAgendamentoUseCase(bookingDAO);
        cancelarAgendamentoUseCase = new CancelarAgendamentoUseCase(bookingDAO);
        filtrarFuncionariosAgendadosPorDiaUseCase = new FiltrarFuncionariosAgendadosPorDiaUseCase(userDAO, bookingDAO, serviceDAO);
    }

    private static void createUser() {
        Address admAddress = new Address("Av. São Carlos", "2120", "", "SP", "São Carlos");
        User adm = new User("Black Bear ADM", "blackbear@adm.com", "(16) 99999-9999", admAddress, "BB", "123", Boolean.TRUE);
        adm.addRole(Role.ADMIN);
        adm.addDay(Day.MONDAY);
        adm.addDay(Day.TUESDAY);
        adm.addDay(Day.WEDNESDAY);
        adm.addDay(Day.THURSDAY);

        Address e00Address = new Address("Rua Barão do Rio Branco", "0", "Sítio Paecara (Vicente de Carvalho)", "SP", "Guarujá");
        User e00 = new User("Grace Hopper", "grace.hopper@email.com", "(13) 2745-5802", e00Address, "Grace", "Hopper", Boolean.TRUE);
        e00.addRole(Role.EMPLOYEE);
        e00.addDay(Day.MONDAY);
        e00.addDay(Day.TUESDAY);
        e00.addDay(Day.WEDNESDAY);

        try {
            cadastrarFuncionarioUseCase.create(adm);
            System.out.println("> SUCCESS .....: Administrator created");
            cadastrarFuncionarioUseCase.create(e00);
            System.out.println("> SUCCESS .....: Employee created");
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

        try {
            cadastrarClienteUseCase.create(client1);
            System.out.println("> SUCCESS .....: Client created");
        } catch (Exception e) {
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }

    private static void injectCreateService() {
        Service s0 = new Service("Corte de cabelo e barba", new BigDecimal("45.0"), new BigDecimal("0.48"), new BigDecimal("0.1"), Boolean.TRUE);
        s0.addType(Type.HAIR);
        s0.addType(Type.BEARD);

        try {
            cadastrarServicosUseCase.create(s0);
            System.out.println("> SUCCESS .....: Service created");
        } catch (Exception e) {
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }

    private static void injectCreateBooking() {
        Client client = listarClientesUseCase.findOne(1);
        Service service = listarServicosUseCase.findOne(1);
        User user1 = listarFuncionariosUseCase.findOne(1);

        Booking booking01 = new Booking(LocalDate.now(), false, client, service, user1);
        Booking booking02 = new Booking(LocalDate.now(), false, client, service, user1);
        Booking booking03 = new Booking(LocalDate.now(), false, client, service, user1);
        Booking booking04 = new Booking(LocalDate.now(), false, client, service, user1);
        Booking booking05 = new Booking(LocalDate.now(), false, client, service, user1);

        try {
            criarAgendamentoUseCase.create(booking01);
            System.out.println("> SUCCESS .....: Booking created");
            criarAgendamentoUseCase.create(booking02);
            System.out.println("> SUCCESS .....: Booking created");
            criarAgendamentoUseCase.create(booking03);
            System.out.println("> SUCCESS .....: Booking created");
            criarAgendamentoUseCase.create(booking04);
            System.out.println("> SUCCESS .....: Booking created");
            criarAgendamentoUseCase.create(booking05);
            System.out.println("> SUCCESS .....: Booking created");
        } catch (Exception e) {
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }

    private static void completeBooking() {
        Client client = listarClientesUseCase.findOne(1);
        Service service = listarServicosUseCase.findOne(1);
        User user1 = listarFuncionariosUseCase.findOne(1);

        Booking bookingComplete = new Booking(LocalDate.now(), false, client, service, user1);

        try {
            concluirAgendamentoUseCase.update(bookingComplete);
            System.out.println("> SUCCESS .....: Booking completed");
        } catch (Exception e) {
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }

    private static void canceledBooking() {
        Client client = listarClientesUseCase.findOne(1);
        Service service = listarServicosUseCase.findOne(1);
        User user1 = listarFuncionariosUseCase.findOne(1);

        Booking bookingCanceled = new Booking(LocalDate.now(), false, client, service, user1);

        try {
            cancelarAgendamentoUseCase.update(bookingCanceled);
            System.out.println("> SUCCESS .....: Booking canceled");
        } catch (Exception e) {
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }

    private static void filterBookingByDay() {
        Client client = listarClientesUseCase.findOne(1);
        Service service = listarServicosUseCase.findOne(1);
        User user1 = listarFuncionariosUseCase.findOne(1);

        Booking booking01 = new Booking(LocalDate.now(), false, client, service, user1);
        Booking booking02 = new Booking(LocalDate.now(), false, client, service, user1);
        Booking booking03 = new Booking(LocalDate.now(), false, client, service, user1);
        Booking booking04 = new Booking(LocalDate.now(), false, client, service, user1);
        Booking booking05 = new Booking(LocalDate.now(), false, client, service, user1);

        try{
            criarAgendamentoUseCase.create(booking01);
            System.out.println("> SUCCESS .....: Booking created");
            criarAgendamentoUseCase.create(booking02);
            System.out.println("> SUCCESS .....: Booking created");
            criarAgendamentoUseCase.create(booking03);
            System.out.println("> SUCCESS .....: Booking created");
            criarAgendamentoUseCase.create(booking04);
            System.out.println("> SUCCESS .....: Booking created");
            criarAgendamentoUseCase.create(booking05);
            System.out.println("> SUCCESS .....: Booking created");

            System.out.println("> SUCCESS .....: Filter by day");
            for(Booking booking : filtrarFuncionariosAgendadosPorDiaUseCase.findByDay()) {
                System.out.println(booking.toString());
            }

            System.out.println("> SUCCESS .....: Filter by user");
            for(Booking booking : filtrarFuncionariosAgendadosPorDiaUseCase.findByUser(user)) {
                System.out.println(booking.toString());
            }

            System.out.println("> SUCCESS .....: Filter by service");
            for(Booking booking : filtrarFuncionariosAgendadosPorDiaUseCase.findByService(service)) {
                System.out.println(booking.toString());
            }
        } catch (Exception e) {
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }
}
