// TravelItem.java
package com.example.myapplication;

public class TravelItem {
    private String title;
    private String country;
    private String startDate;
    private String endDate;

    public TravelItem() {}

    public TravelItem(String title, String country, String startDate, String endDate) {
        this.title = title;
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
}
