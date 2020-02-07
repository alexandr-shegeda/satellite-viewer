package org.stellite.antenna.service;

public enum  PartOfTheDay {

    MORNING("morning"),
    DAY("day"),
    NIGHT("night");

    private final String text;

    PartOfTheDay(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
