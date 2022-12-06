package br.ifsp.edu.blackbearbarbearia.domain.entities.user;

import java.time.DayOfWeek;
import java.util.List;

public class User {
    private Integer id;
    private String fullName;
    private String email;
    private String phone;
    private Address address;
    private String login;
    private String passwordHash;
    private Boolean active;
    private Role role;
    private List<DayOfWeek> days;

    public User() {}

    public User(Integer id, String fullName, String email, String phone, Address address, String login, String passwordHash, Boolean active, Role role, List<DayOfWeek> days) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.login = login;
        this.passwordHash = passwordHash;
        this.active = active;
        this.role = role;
        this.days = days;
    }

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Boolean isActive() {
        return active;
    }

    public Role getRole() {
        return role;
    }

    public List<DayOfWeek> getDays() {
        return days;
    }

    public void clearPasswordHash() {
        this.passwordHash = "";
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address.toString() +
                ", login='" + login + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", active=" + active +
                ", role=" + role +
                ", days=" + days +
                '}';
    }
}
