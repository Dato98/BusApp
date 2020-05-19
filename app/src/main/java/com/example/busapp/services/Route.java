package com.example.busapp.services;

public class Route {
    private String Id;
    private String RouteNumber;
    private String StopA;
    private String StopB;

    public Route(){}

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getRouteNumber() {
        return RouteNumber;
    }

    public void setRouteNumber(String routeNumber) {
        RouteNumber = routeNumber;
    }

    public String getStopA() {
        return StopA;
    }

    public void setStopA(String stopA) {
        StopA = stopA;
    }

    public String getStopB() {
        return StopB;
    }

    public void setStopB(String stopB) {
        StopB = stopB;
    }


}
