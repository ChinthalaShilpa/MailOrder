package com.pack.pojo;

import java.util.Date;
import java.util.List;

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

public class DrugDetail {
	
	
	private String drugId;
	
	private String drugName;
	
	private String manufacturerName;
	private Date manufacturedDate;
	private Date expiryDate;
	
	private List<DrugLocation> drugLocationList;

}
