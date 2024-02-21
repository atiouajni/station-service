package com.redhat.factory.sensors;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SensorDataRaw {
	
	private Long gasStationId;
	private String fuel;
	private String type;
	private Object value;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="GMT")
	private Date date;
	
	public SensorDataRaw() {}
	
	public SensorDataRaw(Long gasStationId, String fuel, String type, Object value, Date date) {
		this.gasStationId = gasStationId;
		this.fuel = fuel;
		this.type = type;
		this.value = value;
		this.date = date;
	}
	
	public Long getGasStationId() {
		return gasStationId;
	}
	public void setGasStationId(Long gasStationId) {
		this.gasStationId = gasStationId;
	}
	public String getFuel() {
		return fuel;
	}
	public void setFuel(String fuel) {
		this.fuel = fuel;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
