package com.pack.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "Model object that stores the Package information.")
public class MemberPrescriptionModel {


	private String memberId;
	private String memberLocation;
	private String date;
	private int quantity;
	private String drugName;
	private String doctorName;  
	private String course;
   
	
}
