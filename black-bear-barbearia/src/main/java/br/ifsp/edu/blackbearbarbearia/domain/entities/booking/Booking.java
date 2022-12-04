package br.ifsp.edu.blackbearbarbearia.domain.entities.booking;

import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Booking {
    private Integer id;
    private LocalDate date;
    private Boolean paid;
    private Client client;
    private Service service;
    private User employee;
    private Status status;

    public Booking(LocalDate date, boolean paid, Client client, Service service, User employee) {
        this.date = date;
        this.paid = paid;
        this.client = client;
        this.service = service;
        this.employee = employee;
        this.status = Status.BOOKED;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    public LocalDate getNoFormattedDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String isPaid() {
        return paid == Boolean.TRUE ? "PAGO" : "À PAGAR";
    }
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getClient() { return client.getName(); }
    public Client getInfoClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public String getService() {
        return service.getName();
    }
    public Service getInfoService() {
        return service;
    }
    public void setService(Service service) {
        this.service = service;
    }

    public String getEmployee() {
        return employee.getFullName();
    }
    public User getInfoEmployee() {
        return employee;
    }
    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isPastDate() {
        LocalDate today = LocalDate.now();

        return this.date.isBefore(today);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", date=" + date +
                ", paid=" + paid +
                ", client=" + client +
                ", service=" + service +
                ", user=" + employee +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}