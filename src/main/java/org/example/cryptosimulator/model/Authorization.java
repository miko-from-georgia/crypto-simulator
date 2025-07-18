package org.example.cryptosimulator.model;

import jakarta.persistence.*;

@Entity
public class Authorization {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(mappedBy = "authorization")
    private Account account;

    private String login;
    private String password;
    private String email;
    private String role = "ROLE_USER";
    public Authorization() {
    }

    public Authorization(Long id, String email, String password, String login) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.login = login;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
