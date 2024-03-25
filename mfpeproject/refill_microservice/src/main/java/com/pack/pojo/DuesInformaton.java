package com.pack.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DuesInformaton {

	private long subsId;
	private String  memId;
	private long daysLagging;
	private String drugName;
	
}
