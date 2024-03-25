package com.pack.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.pack.exception.AuthorizationException;
import com.pack.exception.DrugDetailNotFoundException;
import com.pack.exception.DrugLocationNotFound;
import com.pack.pojo.DrugDetail;
import com.pack.pojo.Stock;

import io.swagger.annotations.ApiOperation;

@FeignClient(name = "Drug-microservice", url = "http://localhost:8100/drug")
public interface DrugClient {

	@GetMapping("/searchdrugbyid/{id}")
	public DrugDetail searchDrugById(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, @PathVariable("id") String id)
			throws AuthorizationException, DrugDetailNotFoundException;
	
	@GetMapping("/searchdrugbyname/{name}")
	@ApiOperation(notes = "Returns the list of specialists along with their experience and contact details", value = "Find specialists")
	public DrugDetail searchDrugByName(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, @PathVariable("name") String name)
			throws AuthorizationException, DrugDetailNotFoundException;
	
	@GetMapping("/searchbylocationanddrugid/{id}/{location}")
	public Stock searchByLocationandDrugId(	@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, @PathVariable("id") String id, @PathVariable("location") String location) throws AuthorizationException, DrugLocationNotFound, DrugDetailNotFoundException;
	
}
