package com.retrotech.triage.Models;

import android.graphics.Color;

import java.util.Objects;

public class TriageCalendar {
    // instance variables
    private String name;
    private Color color;

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    // equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TriageCalendar that = (TriageCalendar) o;
        return name.equals(that.name) &&
                color.equals(that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color);
    }

    // toString
    @Override
    public String toString() {
        return "TriageCalendar{" +
                "name='" + name + '\'' +
                ", color=" + color +
                '}';
    }
}
