package com.example.busapp.services;

public class Monitor {
    private String RouteNumber;
    private String Destination;
    private String Minutes;

    public Monitor(String routeNumber, String destination, String minutes) {
        RouteNumber = routeNumber;
        Destination = destination;
        Minutes = minutes;
    }

    public String getRouteNumber() {
        return RouteNumber;
    }

    public void setRouteNumber(String routeNumber) {
        RouteNumber = routeNumber;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getMinutes() {
        return Minutes;
    }

    public void setMinutes(String minutes) {
        Minutes = minutes;
    }
}
