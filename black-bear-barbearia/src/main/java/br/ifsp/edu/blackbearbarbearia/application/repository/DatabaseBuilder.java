package br.ifsp.edu.blackbearbarbearia.application.repository;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class DatabaseBuilder {
    public static void main(String[] args) throws SQLException, IOException {
        dropTableIfExists();

        createTableDay();
        createTableUser();
        createTableAddress();
        createTableUserDay();
        createTableService();
        createTableClient();
        createTableBooking();

        populateDay();
        populateUser();
        populateAddress();
        populateUserDay();
        populateService();
        populateClient();
        populateBooking();
    }

    private static void dropTableIfExists() throws IOException {
        final Path path = Paths.get("blackbearbarbearia.db");
        if(Files.exists(path)) Files.delete(path);
    }

    private static void createTableDay() throws SQLException {
        final Connection connection = DriverManager.getConnection("jdbc:sqlite:blackbearbarbearia.db");
        final Statement stmt = connection.createStatement();

        String sql = """
                CREATE TABLE day(
                    id INTEGER PRIMARY KEY,
                    name TEXT NOT NULL
                )
                """;
        stmt.executeUpdate(sql);
        stmt.close();
        connection.close();
    }

    private static void createTableUser() throws SQLException {
        final Connection connection = DriverManager.getConnection("jdbc:sqlite:blackbearbarbearia.db");
        final Statement stmt = connection.createStatement();
        String sql = """
                CREATE TABLE user(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    fullName TEXT NOT NULL,
                    email TEXT NOT NULL,
                    phone TEXT NOT NULL,
                    login TEXT NOT NULL,
                    passwordHash TEXT NOT NULL,
                    active BOOLEAN NOT NULL,
                    admin BOOLEAN NOT NULL
                )
                """;
        stmt.executeUpdate(sql);
        stmt.close();
        connection.close();
    }

    private static void createTableAddress() throws SQLException {
        final Connection connection = DriverManager.getConnection("jdbc:sqlite:blackbearbarbearia.db");
        final Statement stmt = connection.createStatement();
        String sql = """
                CREATE TABLE address(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    address TEXT NOT NULL,
                    number TEXT NOT NULL,
                    complement TEXT NOT NULL,
                    district TEXT NOT NULL,
                    city TEXT NOT NULL,
                    userId INTEGER NOT NULL,
                    FOREIGN KEY (userId) REFERENCES user(id)
                )
                """;
        stmt.executeUpdate(sql);
        stmt.close();
        connection.close();
    }

    private static void createTableUserDay() throws SQLException {
        final Connection connection = DriverManager.getConnection("jdbc:sqlite:blackbearbarbearia.db");
        final Statement stmt = connection.createStatement();

        String sql = """
                CREATE TABLE userDay(
                    userId INTEGER,
                    dayId INTEGER,
                    PRIMARY KEY(userId, dayId),
                    FOREIGN KEY (userId) REFERENCES user(id),
                    FOREIGN KEY (dayId) REFERENCES day(id)
                )
                """;
        stmt.executeUpdate(sql);
        stmt.close();
        connection.close();
    }

    private static void createTableService() throws SQLException {
        final Connection connection = DriverManager.getConnection("jdbc:sqlite:blackbearbarbearia.db");
        final Statement stmt = connection.createStatement();
        String sql = """
                CREATE TABLE service(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    price REAL NOT NULL,
                    comissionPercentage REAL NOT NULL,
                    taxPercentage REAL NOT NULL,
                    active BOOLEAN NOT NULL
                )
                """;
        stmt.executeUpdate(sql);
        stmt.close();
        connection.close();
    }

    private static void createTableClient() throws SQLException {
        final Connection connection = DriverManager.getConnection("jdbc:sqlite:blackbearbarbearia.db");
        final Statement stmt = connection.createStatement();
        String sql = """
                CREATE TABLE client(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    email TEXT NOT NULL,
                    phone TEXT NOT NULL
                )
                """;
        stmt.executeUpdate(sql);
        stmt.close();
        connection.close();
    }

    private static void createTableBooking() throws SQLException {
        final Connection connection = DriverManager.getConnection("jdbc:sqlite:blackbearbarbearia.db");
        final Statement stmt = connection.createStatement();
        String sql = """
                CREATE TABLE booking(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    date DATE NOT NULL,
                    paid BOOLEAN NOT NULL,
                    clientId INTEGER NOT NULL,
                    serviceId INTEGER NOT NULL,
                    userId INTEGER NOT NULL,
                    FOREIGN KEY (clientId) REFERENCES client(id),
                    FOREIGN KEY (serviceId) REFERENCES service(id),
                    FOREIGN KEY (userId) REFERENCES user(id)
                )
                """;
        stmt.executeUpdate(sql);
        stmt.close();
        connection.close();
    }

    private static void populateDay() throws SQLException {
        final Connection connection = DriverManager.getConnection("jdbc:sqlite:blackbearbarbearia.db");
        final Statement stmt = connection.createStatement();

        final String sql = "INSERT INTO day (name) " +
                "VALUES ('%s')";

        stmt.addBatch(String.format(sql, DayOfWeek.SUNDAY));
        stmt.addBatch(String.format(sql, DayOfWeek.MONDAY));
        stmt.addBatch(String.format(sql, DayOfWeek.TUESDAY));
        stmt.addBatch(String.format(sql, DayOfWeek.WEDNESDAY));
        stmt.addBatch(String.format(sql, DayOfWeek.THURSDAY));
        stmt.addBatch(String.format(sql, DayOfWeek.FRIDAY));
        stmt.addBatch(String.format(sql, DayOfWeek.SATURDAY));
        stmt.executeBatch();

        stmt.close();
        connection.close();
    }

    private static void populateUser() throws SQLException {
        final Connection connection = DriverManager.getConnection("jdbc:sqlite:blackbearbarbearia.db");
        final Statement stmt = connection.createStatement();

        final String sql = "INSERT INTO user (fullName, email, phone, login, passwordHash, active, admin) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s', %b, %b)";

        stmt.addBatch(String.format(sql, "Black Bear ADM", "blackbear@adm.com", "(16) 99999-9999", "BB", "123", Boolean.TRUE, Boolean.TRUE));
        stmt.addBatch(String.format(sql, "Grace Hopper", "grace.hopper@email.com", "(13) 2745-5802", "Grace", "Hopper", Boolean.TRUE, Boolean.FALSE));
        stmt.addBatch(String.format(sql, "John Backus", "john.backus@email.com", "(24) 2208-8210", "John", "Backus", Boolean.TRUE, Boolean.FALSE));
        stmt.addBatch(String.format(sql, "Bill Gates", "gates.bill@email.com", "(33) 2544-6585", "Gates", "Bill", Boolean.TRUE, Boolean.FALSE));
        stmt.addBatch(String.format(sql, "Brian Kernighan", "brian.kernighan@email.com", "(45) 2583-1359", "Brian", "Brian", Boolean.TRUE, Boolean.FALSE));
        stmt.addBatch(String.format(sql, "Ken Thompson", "ken.thompson@email.com", "(65) 2642-6225", "Thompson", "Ken", Boolean.TRUE, Boolean.FALSE));
        stmt.executeBatch();

        stmt.close();
        connection.close();
    }

    private static void populateAddress() throws SQLException {
        final Connection connection = DriverManager.getConnection("jdbc:sqlite:blackbearbarbearia.db");
        final Statement stmt = connection.createStatement();

        final String sql = "INSERT INTO address (address, number, complement, district, city, userId) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s', %d)";

        stmt.addBatch(String.format(sql, "Av. São Carlos", "2120", "", "SP", "São Carlos", 1));
        stmt.addBatch(String.format(sql, "Rua Barão do Rio Branco", "0", "Sítio Paecara (Vicente de Carvalho)", "SP", "Guarujá", 2));
        stmt.addBatch(String.format(sql, "Rua Tenente Vítor Batista", "1", "Realengo", "RJ", "Rio de Janeiro", 3));
        stmt.addBatch(String.format(sql, "Rua dos Crenaques", "2", "", "MG", "Belo Horizonte", 4));
        stmt.addBatch(String.format(sql, "Rua Suzano", "3", "", "PR", "Foz do Iguaçu", 5));
        stmt.addBatch(String.format(sql, "Rua Isaura Magdalena", "4", "São Matheus", "MT", "Várzea Grande", 6));
        stmt.executeBatch();

        stmt.close();
        connection.close();
    }

    private static void populateUserDay() throws SQLException {
        final Connection connection = DriverManager.getConnection("jdbc:sqlite:blackbearbarbearia.db");
        final Statement stmt = connection.createStatement();

        final String sql = "INSERT INTO userDay (userId, dayId) " +
                "VALUES (%d, %d)";

        stmt.addBatch(String.format(sql, 1, 2));
        stmt.addBatch(String.format(sql, 1, 3));
        stmt.addBatch(String.format(sql, 1, 4));
        stmt.addBatch(String.format(sql, 1, 5));

        stmt.addBatch(String.format(sql, 2, 2));
        stmt.addBatch(String.format(sql, 2, 3));
        stmt.addBatch(String.format(sql, 2, 4));

        stmt.addBatch(String.format(sql, 3, 2));
        stmt.addBatch(String.format(sql, 3, 3));
        stmt.addBatch(String.format(sql, 3, 5));
        stmt.addBatch(String.format(sql, 3, 7));

        stmt.addBatch(String.format(sql, 4, 6));

        stmt.addBatch(String.format(sql, 5, 5));
        stmt.addBatch(String.format(sql, 5, 7));

        stmt.addBatch(String.format(sql, 6, 3));
        stmt.addBatch(String.format(sql, 6, 4));
        stmt.addBatch(String.format(sql, 6, 5));
        stmt.addBatch(String.format(sql, 6, 6));

        stmt.executeBatch();

        stmt.close();
        connection.close();
    }

    private static void populateService() throws SQLException {
        final Connection connection = DriverManager.getConnection("jdbc:sqlite:blackbearbarbearia.db");
        final Statement stmt = connection.createStatement();

        final String sql = "INSERT INTO service (name, price, comissionPercentage, taxPercentage, active) " +
                "VALUES ('%s', '%.2f', '%.2f', '%.2f', %b)";

        stmt.addBatch(String.format(sql, "Corte de cabelo e barba", new BigDecimal("45.00"), new BigDecimal("0.48"), new BigDecimal("0.10"), Boolean.TRUE));
        stmt.addBatch(String.format(sql, "Corte de cabelo", new BigDecimal("30.00"), new BigDecimal("0.40"), new BigDecimal("0.08"), Boolean.TRUE));
        stmt.addBatch(String.format(sql, "Corte de barba", new BigDecimal("25.00"), new BigDecimal("0.40"), new BigDecimal("0.08"), Boolean.TRUE));
        stmt.addBatch(String.format(sql, "Risquinho de cria", new BigDecimal("15.00"), new BigDecimal("0.35"), new BigDecimal("0.05"), Boolean.TRUE));
        stmt.executeBatch();

        stmt.close();
        connection.close();
    }

    private static void populateClient() throws SQLException {
        final Connection connection = DriverManager.getConnection("jdbc:sqlite:blackbearbarbearia.db");
        final Statement stmt = connection.createStatement();

        final String sql = "INSERT INTO client (name, email, phone) " +
                "VALUES ('%s', '%s', '%s')";

        stmt.addBatch(String.format(sql, "Patrícia Celestino Montilla", "patricia.montilla@gmail.com", "(89) 99963-7882"));
        stmt.addBatch(String.format(sql, "Lucimberto Cristo Leonicio", "lucimberto.leonicio@gmail.com.br", "(83) 96938-6221"));
        stmt.addBatch(String.format(sql, "Marcele Amaral Thomaz", "marcele.thomaz@gmail.com.br", "(88) 98326-2818"));
        stmt.addBatch(String.format(sql, "Wellington Vasgestian Quintela", "wellington.quintela@gmail.com.br", "(16) 97927-9672"));
        stmt.addBatch(String.format(sql, "Ataíde Guerini Facre", "ataide.facre@gmail.com.br", "(92) 96876-2986"));
        stmt.executeBatch();

        stmt.close();
        connection.close();
    }

    private static void populateBooking() throws SQLException {
        final Connection connection = DriverManager.getConnection("jdbc:sqlite:blackbearbarbearia.db");
        final Statement stmt = connection.createStatement();

        final String sql = "INSERT INTO booking (date, paid, clientId, serviceId, userId) " +
                "VALUES ('%s', %b, %d, %d, %d)";

        stmt.addBatch(String.format(sql, LocalDate.of(2022, 11, 9), Boolean.FALSE, 1, 1, 1));
        stmt.addBatch(String.format(sql, LocalDate.of(2022,11,8), Boolean.FALSE, 2, 2, 2));
        stmt.addBatch(String.format(sql, LocalDate.of(2022,11,7), Boolean.FALSE, 3, 3, 3));
        stmt.addBatch(String.format(sql, LocalDate.of(2022,11,6), Boolean.FALSE, 4, 4, 4));
        stmt.executeBatch();

        stmt.close();
        connection.close();
    }
}
