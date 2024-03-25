package com.pack.entity;

import java.time.LocalDate;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "Model object that stores the Package information.")
@Entity
@Builder
public class MemberSubscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long subscriptionId;
	private String memberId;
	private String memberLocation;
	private LocalDate date;
	private int quantity;  
	private String drugName;
	private String doctorName;
	private int refillOccurrence;
	private boolean status;
	private String course;
	
	
}
