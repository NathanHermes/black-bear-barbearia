package br.ifsp.edu.blackbearbarbearia.domain.entities.service;

import java.math.BigDecimal;
import java.util.List;

public class ServiceBuilder {
    private Integer id;
    private String name;
    private BigDecimal price;
    private BigDecimal comissionPercentage;
    private BigDecimal taxPercentage;
    private Boolean active;
    private List<Type> types;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setComissionPercentage(BigDecimal comissionPercentage) {
        this.comissionPercentage = comissionPercentage;
    }

    public void setTaxPercentage(BigDecimal taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public Service getResult() {
        return new Service(id, name, price, comissionPercentage, taxPercentage, active, types);
   }
}
