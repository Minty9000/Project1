package com.cs336.pkg;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Reservation {
    public int rid;
    public String train;
	public String trainline;
    public String origin;
    public String destination;
    public LocalDateTime departure_time;
    public LocalDateTime arrival_time;
    public float fare;
    public LocalDateTime reservation_date;
    
    public Reservation(int rid, String train, String trainline, String origin, String destination,
            LocalDateTime departure_time, LocalDateTime arrival_time, float fare,
            LocalDateTime reservation_date) {
		this.rid = rid;
		this.train = train;
		this.trainline = trainline;
		this.origin = origin;
		this.destination = destination;
		this.departure_time = departure_time;
		this.arrival_time = arrival_time;
		this.fare = fare;
		this.reservation_date = reservation_date;
	}
    
}
