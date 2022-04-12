package org.school21.restful.model;

public enum State {
    PUBLISHED("Published"),
    DRAFT("Draft");

    private final String state;

    State(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
