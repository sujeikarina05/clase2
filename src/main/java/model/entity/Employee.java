package model.entity;

import model.enums.Roles;

public class Employee {
    private String id;
    private String names;
    private String lastNames;
    private String password;
    private String salt;
    private Roles role;

    public Employee() {
    }

    public Employee(String id, String names, String lastNames, String password, String salt, Roles role) {
        this.id = id;
        this.names = names;
        this.lastNames = lastNames;
        this.password = password;
        this.salt = salt;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
