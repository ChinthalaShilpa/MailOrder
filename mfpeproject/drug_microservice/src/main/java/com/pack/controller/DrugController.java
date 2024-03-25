package com.pack.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.pack.entity.DrugDetail;
import com.pack.exception.AuthorizationException;
import com.pack.exception.DrugDetailNotFoundException;
import com.pack.exception.DrugLocationNotFound;
import com.pack.feign.AuthorisingClient;
import com.pack.service.DrugService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;


@CrossOrigin
@RestController
@Slf4j
public class DrugController {

	@Autowired
	private AuthorisingClient authorisingClient;
	
	
	@Autowired
	private DrugService service;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DrugController.class);
	
	@GetMapping("/viewdrugs")
	@ApiOperation(notes = "Returns the list of drugs", value = "Get the list of drugs")
	public List<DrugDetail> getAllDrugDetail(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException{
		LOGGER.info("Inside get all drug detail method");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return service.getListOfDrugDetail();
		} else {
			LOGGER.error("AuthorizationException : Not allowed ");
			LOGGER.info("End get all drug detail method");
			throw new AuthorizationException("Not allowed");
		}
	}
	
	@GetMapping("/searchdrugbyid/{id}")
	@ApiOperation(notes = "Returns the drug by id", value = "Find Drug details By Id")
	public DrugDetail searchDrugById(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@ApiParam(name = "id", value = "id of the drug") @PathVariable("id") String id)
			throws AuthorizationException, DrugDetailNotFoundException {
		LOGGER.info("Inside search drug by id method");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return service.getDrugById(id);
		} else {
			LOGGER.error("AuthorizationException : Not allowed ");
			throw new AuthorizationException("Not allowed");
		}
		
	}
	
	@GetMapping("/searchdrugbyname/{name}")
	@ApiOperation(notes = "Returns the drug by name", value = "Find Drug Details By name")
	public DrugDetail searchDrugByName(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, 
			@ApiParam(name = "name", value = "name of the drug") @PathVariable("name") String name)
			throws AuthorizationException, DrugDetailNotFoundException {
		LOGGER.info("Inside search drug by name method");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return service.getDrugByName(name);
		} else {
			LOGGER.error("AuthorizationException : Not allowed ");
			throw new AuthorizationException("Not allowed");
		}  
		
	}
	
	@GetMapping("/searchbylocationanddrugid/{id}/{location}")
	@ApiOperation(notes="Returns the drug by location and id",value="Get drug by location and id")
	public DrugDetail searchByLocationandDrugId(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@ApiParam(name = "id", value = "id of the drug") @PathVariable("id") String id,
			@ApiParam(name = "location", value = "location to find the drug") @PathVariable("location") String location) 
					throws AuthorizationException, DrugLocationNotFound, DrugDetailNotFoundException
	{
		LOGGER.info("Inside search by location and drugid method");
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return service.searchByLocationandDrugId(id, location);
		} else {
			LOGGER.error("AuthorizationException : Not allowed ");
			throw new AuthorizationException("Not allowed");
		}
	}
}
