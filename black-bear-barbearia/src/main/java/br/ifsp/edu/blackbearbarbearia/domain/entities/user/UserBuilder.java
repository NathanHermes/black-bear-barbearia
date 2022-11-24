package br.ifsp.edu.blackbearbarbearia.domain.entities.user;

import java.time.DayOfWeek;
import java.util.List;

public class UserBuilder {
    private String fullName;
    private String email;
    private String phone;
    private Address address;
    private String login;
    private String passwordHash;
    private Boolean active;
    private Role role;
    private List<DayOfWeek> days;

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setDays(List<DayOfWeek> days) {
        this.days = days;
    }

    public User getResult() {
        return new User(fullName, email, phone, address, login, active, passwordHash, role, days);
    }
}
