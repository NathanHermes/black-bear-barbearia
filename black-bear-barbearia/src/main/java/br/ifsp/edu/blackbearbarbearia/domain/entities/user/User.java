package br.ifsp.edu.blackbearbarbearia.domain.entities.user;

import java.time.DayOfWeek;
import java.util.ArrayList;
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
    private String lastPassword;
    private List<Role> roles;
    private List<DayOfWeek> days;

    public User(String login, String passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
    }

    public User(String email, String phone, Address address, Boolean active) {
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.active = active;
    }

    public User(String fullName, String email, String phone, Address address, String login, String passwordHash, Boolean active) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.login = login;
        this.passwordHash = passwordHash;
        this.active = active;
        this.roles = new ArrayList<Role>();
        this.days = new ArrayList<Day>();
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Boolean isActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getLastPassword() {
        return lastPassword;
    }
    public void setLastPassword(String lastPassword) {
        this.lastPassword = lastPassword;
    }

    public List<Day> getDays() {
        return days;
    }

    public boolean isValidPassword(String password) {
        return !password.equals(lastPassword);
    }

    public void clearPasswordHash() {
        this.passwordHash = null;
    }

    public void addRole(Role role) {
        if(!this.roles.contains(role)) this.roles.add(role);
    }
    public void removeRole(Role role) {
        this.roles.remove(role);
    }
    public boolean hasRole(Role role) {
        return !roles.contains(role);
    }

    public void addDay(Day day) {
        if(this.days.size() == 6)
            throw new IllegalArgumentException("Days with work limit reached");
        this.days.add(day);
    }
    public void removeDay(Day day) {
        days.remove(day);
    }

    @Override
    public String toString() {
        return "Employee " + id + "\n" +
                "FullName: " + fullName + "\n";
    }
}
