package com.example.myapplication;

public class FlightItem {
    private String airline;
    private String departureTime;
    private String arrivalTime;
    private long price;

    public FlightItem(String airline, String departureTime, String arrivalTime, int price) {
        this.airline = airline;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
    }

    public String getAirline() { return airline; }
    public String getDepartureTime() { return departureTime; }
    public String getArrivalTime() { return arrivalTime; }
    public long getPrice() { return price; }
}
