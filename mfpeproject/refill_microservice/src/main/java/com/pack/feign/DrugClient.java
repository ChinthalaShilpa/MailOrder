package com.pack.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.pack.exception.AuthorizationException;
import com.pack.exception.DrugDetailNotFoundException;
import com.pack.exception.DrugLocationNotFound;
import com.pack.pojo.DrugDetail;


import io.swagger.annotations.ApiOperation;

@FeignClient(name = "Drugmicroservice", url = "http://localhost:8100/drug")
public interface DrugClient {

	@GetMapping("/searchdrugbyid/{id}")
	public DrugDetail searchDrugById(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, @PathVariable("id") String id)
			throws AuthorizationException, DrugDetailNotFoundException;
	
	@GetMapping("/searchdrugbyname/{name}")
	public DrugDetail searchDrugByName(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, @PathVariable("name") String name)
			throws AuthorizationException, DrugDetailNotFoundException;
	
	@GetMapping("/searchbylocationanddrugid/{id}/{location}")
	public DrugDetail searchByLocationandDrugId(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, @PathVariable("id") String id, @PathVariable("location") String location) throws AuthorizationException, DrugLocationNotFound, DrugDetailNotFoundException;
	
}
