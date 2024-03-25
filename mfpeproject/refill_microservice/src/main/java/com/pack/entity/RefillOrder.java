package com.pack.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Builder
public class RefillOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long refillOrderId;
	private LocalDate refillDate;
	private String memberId;
	private long subsId;
	@JsonProperty
	private boolean paymentStatus;
}
