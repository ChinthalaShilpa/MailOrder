package com.pack.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@ApiModel(value = "Model object that stores the Drug Detail information.")
@Entity
public class DrugDetail {
	
	@Id
	@NotNull(message = "Id cannot be null")
	private String drugId;
	@NotNull(message = "Name cannot be null")
	private String drugName;
	@NotNull(message = "manufacturerName cannot be null")
	private String manufacturerName;
	private Date manufacturedDate;
	private Date expiryDate;
	@OneToMany
	@JoinColumn(name  = "drug_id")
	private List<DrugLocation> drugLocationList;

}
