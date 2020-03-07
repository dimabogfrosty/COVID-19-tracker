package com.example.spring.boot.coronavirustracker.model;

public class Location {

    private String state;
    private String country;
    private long totalCases;
    private long differenceFromPreviousDay;

    public Location() {
    }

    public Location(String state, String country, long totalCases, long differenceFromPreviousDay) {
        this.state = state;
        this.country = country;
        this.totalCases = totalCases;
        this.differenceFromPreviousDay = differenceFromPreviousDay;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public long getTotalCases() {
        return totalCases;
    }

    public long getDifferenceFromPreviousDay() {
        return differenceFromPreviousDay;
    }

    @Override
    public String toString() {
        return "Location{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestTotalCases=" + totalCases +
                '}';
    }
}
