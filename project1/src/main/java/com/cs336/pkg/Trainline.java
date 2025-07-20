package com.cs336.pkg;

public class Trainline {
	public int tlid;
    public String name;
    public Station origin;
    public Station destination;
    public int travel_time;
    public int total_stops;
    public float fare;

    public Trainline(int tlid, String name, Station origin, Station destination, int travelTime, int total_stops, float fare) {
        this.tlid = tlid;
        this.name = name;
        this.origin = origin;
        this.destination = destination;
        this.travel_time = travelTime;
        this.total_stops = total_stops;
        this.fare = fare;
    }
}
