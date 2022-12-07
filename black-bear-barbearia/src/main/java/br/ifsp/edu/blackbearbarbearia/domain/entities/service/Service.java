package br.ifsp.edu.blackbearbarbearia.domain.entities.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Service {
    private Integer id;
    private String name;
    private BigDecimal price;
    private BigDecimal comissionPercentage;
    private BigDecimal taxPercentage;
    private Boolean active;
    private List<Type> types;

    public Service() {}

    public Service(Integer id, String name, BigDecimal price, BigDecimal comissionPercentage, BigDecimal taxPercentage, Boolean active, List<Type> types) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.comissionPercentage = comissionPercentage;
        this.taxPercentage = taxPercentage;
        this.active = active;
        this.types = types;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getComissionPercentage() {
        return comissionPercentage;
    }

    public BigDecimal getTaxPercentage() {
        return taxPercentage;
    }

    public Boolean getActive() {
        return active;
    }

    public List<Type> getTypes() {
        return this.types;
    }

    public void addType(Type type) {
        if(!types.contains(type)) this.types.add(type);
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
                ", name='" + name + '\'' +
                ", price=" + price +
                ", comissionPercentage=" + comissionPercentage +
                ", taxPercentage=" + taxPercentage +
                ", active=" + active +
                ", types=" + types +
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