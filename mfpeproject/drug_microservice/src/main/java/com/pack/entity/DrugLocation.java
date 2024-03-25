package com.pack.entity;


import javax.persistence.Entity;
import javax.persistence.Id;

import javax.validation.constraints.NotNull;

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
@Entity
public class DrugLocation {
	
	@Id
	@NotNull(message = "Id cannot be null")
	private int id;
	@NotNull(message = "location cannot be null")
	private String location;
	@NotNull(message = "quantity cannot be null")
	private int quantity;
	/*
	@ManyToOne
	@JoinColumn(name = "drug_id", referencedColumnName = "drugId")
	private DrugDetail drugDetail;
	*/

}
