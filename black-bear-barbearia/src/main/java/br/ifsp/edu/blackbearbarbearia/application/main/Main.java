package br.ifsp.edu.blackbearbarbearia.application.main;

import br.ifsp.edu.blackbearbarbearia.application.repository.inMemory.InMemoryBookingDAO;
import br.ifsp.edu.blackbearbarbearia.application.repository.inMemory.InMemoryClientDAO;
import br.ifsp.edu.blackbearbarbearia.application.repository.inMemory.InMemoryServiceDAO;
import br.ifsp.edu.blackbearbarbearia.application.repository.inMemory.InMemoryUserDAO;
import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;
import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Type;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Address;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.Role;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.UserBuilder;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.booking.*;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.client.ClientDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.client.CreateClientUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.employee.CadastrarFuncionarioUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.CreateServiceUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.ServiceDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.LoginUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static LoginUseCase loginUseCase;
    public static CadastrarFuncionarioUseCase cadastrarFuncionarioUseCase;
    public static CreateClientUseCase createClientUseCase;
    public static CreateServiceUseCase createServiceUseCase;
    public static CreateBookingUseCase createBookingUseCase;
    public static FindBookingUseCase findBookingUseCase;
    public static FinishBookingUseCase finishBookingUseCase;
    public static GenerateNotaFiscalInPDFUseCase generateNotaFiscalInPDFUseCase;
    public static CancelBookingUseCase cancelBookingUseCase;

    public static User USER;
    public static ArrayList<String> INFOCOMMISSIONPOPUP;

    public static void main(String[] args) {
        configureInjection();
        testsInjection();
        WindowLoader.main(args);
    }

    private static void testsInjection() {
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

        Client client = new Client("Patrícia Celestino Montilla", "patricia.montilla@gmail.com", "(89) 99963-7882");
        Service service = new Service("Corte de cabelo e barba", new BigDecimal("45.0"), new BigDecimal("0.48"), new BigDecimal("0.1"), Boolean.TRUE);
        service.addType(Type.HAIR);
        service.addType(Type.BEARD);

        Booking booking = new Booking(LocalDate.now(), false, client, service, adm);

        try {
            cadastrarFuncionarioUseCase.create(adm);
            createClientUseCase.create(client);
            createServiceUseCase.create(service);
            createBookingUseCase.create(booking);
            System.out.println("> SUCCESS .....: Injection is complete");
        }
        catch (Exception e) {
            System.out.println("\n> ERROR ...: " + e.getMessage() + "\n");
        }
    }

    private static void configureInjection() {
        INFOCOMMISSIONPOPUP = new ArrayList<>();

        UserDAO userDAO = new InMemoryUserDAO();
        loginUseCase = new LoginUseCase(userDAO);
        cadastrarFuncionarioUseCase = new CadastrarFuncionarioUseCase(userDAO);

        ClientDAO clientDAO = new InMemoryClientDAO();
        createClientUseCase = new CreateClientUseCase(clientDAO);

        ServiceDAO serviceDAO = new InMemoryServiceDAO();
        createServiceUseCase = new CreateServiceUseCase(serviceDAO);

        BookingDAO bookingDAO = new InMemoryBookingDAO();
        createBookingUseCase = new CreateBookingUseCase(bookingDAO);
        findBookingUseCase = new FindBookingUseCase(bookingDAO);
        finishBookingUseCase = new FinishBookingUseCase(bookingDAO);
        generateNotaFiscalInPDFUseCase = new GenerateNotaFiscalInPDFUseCase();
        cancelBookingUseCase = new CancelBookingUseCase(bookingDAO);
    }
}
