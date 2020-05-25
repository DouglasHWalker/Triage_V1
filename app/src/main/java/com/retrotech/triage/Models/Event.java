package com.retrotech.triage.Models;

import java.util.Objects;

public class Event {

    // instance variables
    private String title;
    private long startTime;
    private long endTime;
    private TriageCalendar tCalendar;
    private Alert alert;
    private boolean complete = false;

    public Event(String title, long startTime, long endTime, TriageCalendar tCalendar, Alert alert, boolean complete) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tCalendar = tCalendar;
        this.alert = alert;
        this.complete = complete;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public TriageCalendar gettCalendar() {
        return tCalendar;
    }

    public void settCalendar(TriageCalendar tCalendar) {
        this.tCalendar = tCalendar;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return startTime == event.startTime &&
                endTime == event.endTime &&
                complete == event.complete &&
                title.equals(event.title) &&
                tCalendar.equals(event.tCalendar) &&
                Objects.equals(alert, event.alert);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, startTime, endTime, tCalendar, alert, complete);
    }
}
