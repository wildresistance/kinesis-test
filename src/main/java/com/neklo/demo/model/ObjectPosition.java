package com.neklo.demo.model;

import lombok.Data;

@Data
public class ObjectPosition {
	public String object; // will be a partition key
	public double latitude;
	public double longitude;
	public long timestamp;
}
