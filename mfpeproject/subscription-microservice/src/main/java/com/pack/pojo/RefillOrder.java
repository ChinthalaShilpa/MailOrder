package com.pack.pojo;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class RefillOrder {
	private long refillOrderId;
	private LocalDate refillDate;
	//private int quantity;
	private long subsId;
	@JsonProperty
	private boolean paymentStatus;
}
