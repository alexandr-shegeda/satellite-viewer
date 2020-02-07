package org.satellite.antenna.model;

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
