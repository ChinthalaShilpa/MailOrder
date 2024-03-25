package com.pack.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefillPojo {

	@JsonProperty
	private boolean statusOk;
	//private int quantity;
	private String mid;
	private long sid;
}
