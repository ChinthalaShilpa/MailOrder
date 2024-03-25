package com.pack.pojo;

import java.time.LocalDate;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "Model object that stores the Member Subscription information.")
public class MemberSubscription {

	
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
