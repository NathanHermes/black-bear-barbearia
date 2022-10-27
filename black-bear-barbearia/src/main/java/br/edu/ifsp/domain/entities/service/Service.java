package br.edu.ifsp.domain.entities.service;

import br.edu.ifsp.domain.entities.booking.Booking;

import java.math.BigDecimal;

public class Service {
    private Integer id;
    private String nome;
    private BigDecimal price;
    private BigDecimal comissionPercentage;
    private BigDecimal taxPercentage;
    private Boolean active;

    private Type type;
    private Booking booking;

    public void addType(Type type) {}
    public void removeType(Type type) {}
    public boolean isOneOfType(Type type) {
        return false;
    }
    public BigDecimal calculateComission() {
        return null;
    }
    public BigDecimal calculateTax() {
        return null;
    }
    public BigDecimal calculateNetPrice() {
        return null;
    }

}
