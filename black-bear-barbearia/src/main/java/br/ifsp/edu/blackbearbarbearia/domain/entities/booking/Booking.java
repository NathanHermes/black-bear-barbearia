package br.ifsp.edu.blackbearbarbearia.domain.entities.booking;

import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;

import java.time.LocalDate;
import java.util.Objects;

public class Booking {
    private Integer id;
    private LocalDate date;
    private Boolean paid;

    private Client client;
    private Service service;
    private User user;
    private Status status;

    public Booking(int id, LocalDate date, boolean paid, Client client, Service service, User user) {
        this.id = id;
        this.date = date;
        this.paid = paid;
        this.client = client;
        this.service = service;
        this.user = user;
        this.status = Status.BOOKED;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isPastDate() {
        LocalDate today = LocalDate.now();

        if(this.date.isBefore(today)) return true;
        return false;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", date=" + date +
                ", paid=" + paid +
                ", client=" + client +
                ", service=" + service +
                ", user=" + user +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return id == booking.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
