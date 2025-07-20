package com.cs336.pkg;

import java.time.LocalDateTime;

public class Schedule {
	public int scid;
	public String train;
	public String trainline;
	public LocalDateTime departure_time;
	public LocalDateTime arrival_time;
	
	public Schedule (int scid, String train, String trainline, LocalDateTime departure_time, LocalDateTime arrival_time) {
		this.scid = scid;
		this.train = train;
		this.trainline = trainline;
		this.departure_time = departure_time;
		this.arrival_time = arrival_time;
	}
}
