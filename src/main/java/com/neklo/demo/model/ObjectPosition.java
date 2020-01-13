package com.neklo.demo.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ObjectPosition implements Serializable {
	public String object; // will be a partition key
	public BigDecimal latitude;
	public BigDecimal longitude;
	public LocalDateTime timestamp;
}
