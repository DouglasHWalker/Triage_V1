package com.retrotech.triage.Models;

import java.util.Objects;

public class Alert {

    // instance variables
    private long startTime;
    private String label;
    private String alarmSound;

    public Alert(long startTime, String label, String alarmSound) {
        this.startTime = startTime;
        this.label = label;
        this.alarmSound = alarmSound;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAlarmSound() {
        return alarmSound;
    }

    public void setAlarmSound(String alarmSound) {
        this.alarmSound = alarmSound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alert alert = (Alert) o;
        return startTime == alert.startTime &&
                label.equals(alert.label) &&
                Objects.equals(alarmSound, alert.alarmSound);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, label, alarmSound);
    }

    // toString

    @Override
    public String toString() {
        return "Alert{" +
                "startTime=" + startTime +
                ", label='" + label + '\'' +
                ", alarmSound='" + alarmSound + '\'' +
                '}';
    }
}
