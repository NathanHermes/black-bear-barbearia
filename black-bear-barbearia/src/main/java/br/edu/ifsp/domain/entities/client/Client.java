package br.edu.ifsp.domain.entities.client;

import br.edu.ifsp.domain.entities.booking.Booking;

import java.util.ArrayList;

public class Client {
    private String id;
    private String name;
    private String email;
    private String phone;

    private ArrayList<Booking> bookings;
}
