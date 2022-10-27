package br.edu.ifsp.domain.entities.booking;

import br.edu.ifsp.domain.entities.client.Client;
import br.edu.ifsp.domain.entities.service.Service;

import java.time.LocalDate;

public class Booking {
    private int id;
    private LocalDate date;
    private boolean paid;
    private Client client;
    private Service service;

    public Booking(int id, LocalDate date, boolean paid) {
        this.id = id;
        this.date = date;
        this.paid = paid;
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

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isPastDate() {return false;}
}
