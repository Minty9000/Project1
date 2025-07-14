package com.cs336.pkg;

public class Trainline {
	public int tlid;
    public String name;
    public Station origin;
    public Station destination;
    public int travelTime;
    public int numOfStops;
    public float fare;

    public Trainline(int tlid, String name, Station origin, Station destination, int travelTime, int numOfStops, float fare) {
        this.tlid = tlid;
        this.name = name;
        this.origin = origin;
        this.destination = destination;
        this.travelTime = travelTime;
        this.numOfStops = numOfStops;
        this.fare = fare;
    }
}
