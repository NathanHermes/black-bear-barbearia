package br.ifsp.edu.blackbearbarbearia.application.main;

import br.ifsp.edu.blackbearbarbearia.application.repository.sqlite.*;
import br.ifsp.edu.blackbearbarbearia.application.view.WindowLoader;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.booking.*;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.client.ClientDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.client.CreateClientUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.client.FindClientUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.client.UpdateClientUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.employee.CreateEmployeeUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.employee.FindEmployeeUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.employee.UpdateEmployeeUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.employee.UpdatePasswordUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.CreateServiceUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.FindServiceUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.ServiceDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.UpdateServiceUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.DeletePasswordEmployeeUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.LoginUseCase;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;

import java.util.ArrayList;

public class Main {
    public static LoginUseCase loginUseCase;
    public static CreateEmployeeUseCase createEmployeeUseCase;
    public static UpdateEmployeeUseCase updateEmployeeUseCase;
    public static FindEmployeeUseCase findEmployeeUseCase;
    public static DeletePasswordEmployeeUseCase deletePasswordEmployeeUseCase;
    public static UpdatePasswordUseCase updatePasswordUseCase;
    public static CreateClientUseCase createClientUseCase;
    public static UpdateClientUseCase updateClientUseCase;
    public static FindClientUseCase findClientUseCase;
    public static CreateServiceUseCase createServiceUseCase;
    public static FindServiceUseCase findServiceUseCase;
    public static CreateBookingUseCase createBookingUseCase;
    public static FindBookingUseCase findBookingUseCase;
    public static FinishBookingUseCase finishBookingUseCase;
    public static GenerateNotaFiscalInPDFUseCase generateNotaFiscalInPDFUseCase;
    public static CancelBookingUseCase cancelBookingUseCase;

    public static UpdateServiceUseCase updateServiceUseCase;
    public static User USER;

    public static void main(String[] args) {
        configureInjection();
        WindowLoader.main(args);
    }

    private static void configureInjection() {
        UserDAO userDAO = new SqliteUserDAO();
        loginUseCase = new LoginUseCase(userDAO);
        createEmployeeUseCase = new CreateEmployeeUseCase(userDAO);
        updateEmployeeUseCase = new UpdateEmployeeUseCase(userDAO);
        findEmployeeUseCase = new FindEmployeeUseCase(userDAO);
        deletePasswordEmployeeUseCase = new DeletePasswordEmployeeUseCase(userDAO);
        updatePasswordUseCase = new UpdatePasswordUseCase(userDAO);

        ClientDAO clientDAO = new SqliteClientDAO();
        createClientUseCase = new CreateClientUseCase(clientDAO);
        updateClientUseCase = new UpdateClientUseCase(clientDAO);
        findClientUseCase = new FindClientUseCase(clientDAO);

        ServiceDAO serviceDAO = new SqliteServiceDAO();
        createServiceUseCase = new CreateServiceUseCase(serviceDAO);
        updateServiceUseCase = new UpdateServiceUseCase(serviceDAO);
        findServiceUseCase = new FindServiceUseCase(serviceDAO);

        StatusDAO statusDAO = new SqliteStatusDAO();
        BookingDAO bookingDAO = new SqliteBookingDAO();
        createBookingUseCase = new CreateBookingUseCase(bookingDAO, userDAO, serviceDAO, clientDAO);
        findBookingUseCase = new FindBookingUseCase(bookingDAO);
        finishBookingUseCase = new FinishBookingUseCase(bookingDAO, statusDAO);
        generateNotaFiscalInPDFUseCase = new GenerateNotaFiscalInPDFUseCase();
        cancelBookingUseCase = new CancelBookingUseCase(bookingDAO, statusDAO);
    }
}
