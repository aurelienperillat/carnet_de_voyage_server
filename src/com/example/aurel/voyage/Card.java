package com.example.aurel.voyage;

public class Card {
	private long id, tripId;
	private String filename, text;
	
	public Card(long id,long tripId, String filename, String text) {
		this.id = id;
		this.tripId = tripId;
		this.filename = filename;
		this.text = text;
	}
	
	public long getId() { return id; }
	public void setId(long id) { this.id = id; }
	
	public long getTripId() { return id; }
	public void setTripId(long tripId) { this.tripId = tripId; }
	
	public String getFilename() { return filename; }
	public void setFilename(String filename) { this.filename = filename; }
	
	public String getText() { return text; }
	public void setText(String text) { this.text = text; }
}
