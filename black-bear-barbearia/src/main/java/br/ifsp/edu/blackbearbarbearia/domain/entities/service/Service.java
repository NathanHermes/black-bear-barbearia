package br.ifsp.edu.blackbearbarbearia.domain.entities.service;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Service {
    private Integer id;
    private String nome;
    private BigDecimal price;
    private BigDecimal comissionPercentage;
    private BigDecimal taxPercentage;
    private Boolean active;

    private List<Type> types;
    private Booking booking;

    public Service(String nome, BigDecimal price, BigDecimal comissionPercentage, BigDecimal taxPercentage, Boolean active, Booking booking) {
        this.nome = nome;
        this.price = price;
        this.comissionPercentage = comissionPercentage;
        this.taxPercentage = taxPercentage;
        this.active = active;
        this.types = new ArrayList<>();
        this.booking = booking;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getComissionPercentage() {
        return comissionPercentage;
    }

    public void setComissionPercentage(BigDecimal comissionPercentage) {
        this.comissionPercentage = comissionPercentage;
    }

    public BigDecimal getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(BigDecimal taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public void addType(Type type) {
        if(!types.contains(type)) types.add(type);
    }

    public void removeType(Type type) {
        types.remove(type);
    }

    public boolean isOneOfType(Type type) {
        if(types.contains(type)) return true;
        return false;
    }

    public BigDecimal calculateComission() {
        return price.multiply(comissionPercentage);
    }

    public BigDecimal calculateTax() {
        return price.multiply(taxPercentage);
    }

    public BigDecimal calculateNetPrice() {
        return price.subtract(calculateComission().add(calculateTax()));
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", price=" + price +
                ", comissionPercentage=" + comissionPercentage +
                ", taxPercentage=" + taxPercentage +
                ", active=" + active +
                ", types=" + types +
                ", booking=" + booking +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(id, service.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
