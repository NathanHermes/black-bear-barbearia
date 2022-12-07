package br.ifsp.edu.blackbearbarbearia.domain.entities.booking;

import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;

import java.sql.Date;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Booking {
    private Integer id;
    private Date date;
    private Time hour;
    private Boolean paid;
    private Client client;
    private Service service;
    private User employee;
    private Status status;

    public Booking() {}

    public Booking(Integer id, Date date, Time hour, Boolean paid, Client client, Service service, User employee, Status status) {
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.paid = paid;
        this.client = client;
        this.service = service;
        this.employee = employee;
        this.status = status;
    }

    public Booking(Date date, Time hour, Client client, Service service, User employee) {
        this(null, date, hour, Boolean.FALSE, client, service, employee, Status.BOOKED);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date.toLocalDate().format((DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
    public Date getNoFormattedDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getHour() {
        return String.valueOf(hour);
    }
    public Time getNoFormattedHour() {
        return hour;
    }
    public void setHour(Time hour) {
        this.hour = hour;
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


    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", date=" + date +
                ", paid=" + paid +
                ", client=" + client.toString() +
                ", service=" + service.toString() +
                ", employee=" + employee.toString() +
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