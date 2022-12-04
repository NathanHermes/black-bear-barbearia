package br.ifsp.edu.blackbearbarbearia.domain.entities.user;

import java.time.DayOfWeek;
import java.util.List;

public class User {
    private Integer id;
    private final String fullName;
    private final String email;
    private final String phone;
    private final Address address;
    private final String login;
    private String passwordHash;
    private final Boolean active;
    private String lastPassword;
    private final Role role;
    private final List<DayOfWeek> days;

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

    /*

    public User(String email, String phone, Address address, Boolean active) {
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.active = active;
    }
    */

    public User(String fullName, String email, String phone, Address address, String login, Boolean active, String passwordHash, Role role, List<DayOfWeek> days ){
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

    public void setId(Integer id) {
        this.id = id;
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

    public String getLastPassword() {
        return lastPassword;
    }
    public void setLastPassword(String lastPassword) {
        this.lastPassword = lastPassword;
    }

    public Role getRole() {
        return role;
    }

    public List<DayOfWeek> getDays() {
        return days;
    }

    public void clearPasswordHash() {
        this.passwordHash = null;
    }

    public void addDay(DayOfWeek day) {
        if(this.days.size() == 6)
            throw new IllegalArgumentException("Days with work limit reached");
        this.days.add(day);
    }
    public void removeDay(DayOfWeek day) {
        days.remove(day);
    }

//    @Override
//    public String toString() {
//        return "Employee " + id + "\n" +
//                "FullName: " + fullName + "\n";
//    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                ", login='" + login + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", active=" + active +
                ", lastPassword='" + lastPassword + '\'' +
                ", role=" + role +
                ", days=" + days +
                '}';
    }
}
