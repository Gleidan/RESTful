package org.school21.restful.model;

public enum Role {
    ADMINISTRATOR("ADMINISTRATOR"),
    TEACHER("TEACHER"),
    STUDENT("STUDENT");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
