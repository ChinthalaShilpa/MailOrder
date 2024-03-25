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
@ApiModel(value = "Model object that stores the Drug Location information.")

public class DrugLocation {
	
	
	private int id;
	
	private String location;

	private int quantity;
	/*
	@ManyToOne
	@JoinColumn(name = "drug_id", referencedColumnName = "drugId")
	private DrugDetail drugDetail;
	*/

}
