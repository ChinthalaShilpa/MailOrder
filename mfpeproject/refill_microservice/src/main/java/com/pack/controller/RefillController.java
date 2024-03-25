package com.pack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.pack.entity.RefillOrder;
import com.pack.exception.AuthorizationException;
import com.pack.exception.DrugDetailNotFoundException;
import com.pack.exception.DrugLocationNotFound;
import com.pack.exception.DrugNotAvailableInLocationException;
import com.pack.exception.DueIsFoundException;
import com.pack.exception.MemberSubscriptionNotFoundException;
import com.pack.feign.AuthorisingClient;
import com.pack.feign.DrugClient;
import com.pack.feign.SubscriptionClient;
import com.pack.pojo.DrugDetail;
import com.pack.pojo.DuesInformaton;
import com.pack.pojo.MemberSubscription;
import com.pack.pojo.MyDate;
import com.pack.pojo.RefillPojo;
import com.pack.service.RefillMicroservice;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
public class RefillController {
	

	@Autowired
	AuthorisingClient authorisingClient;
	
	@Autowired
	DrugClient drugClient;
	
	@Autowired
	SubscriptionClient subscriptionClient;
	
	@Autowired
	RefillMicroservice service;
	
	@GetMapping("/viewrefillstatus/{id}")
	@ApiOperation(notes="Returns refill status by subscription id",value="Get refill status")
	public RefillOrder getRefillStatus(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") long id ) throws AuthorizationException, MemberSubscriptionNotFoundException, DueIsFoundException {
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			MemberSubscription member = subscriptionClient.getSubscribeDetails(requestTokenHeader, id);
			if(member!=null) {
				return service.getRefillStatus(member);
			}
			else
				throw new MemberSubscriptionNotFoundException("Member subscription not found");
		}
		else {
			throw new AuthorizationException("Not allowed");
		}
	}
	
	@GetMapping("/getrefillorders/{mid}")
	@ApiOperation(notes="Return the list of refill orders by memberid",value="Get all Refill order details")
	public List<RefillOrder> getAllRefills(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("mid") String mid) throws AuthorizationException{
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return service.getAllRefills(mid);
		}
		else {
			throw new AuthorizationException("Not allowed");
		}
	}
	
	@PostMapping("/requestadhocrefill")
	@ApiOperation(notes="Return adhoc refill status",value="View Refill order details")
	public RefillOrder addRefillOrder(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@RequestBody RefillPojo order) throws AuthorizationException, MemberSubscriptionNotFoundException, DrugNotAvailableInLocationException, DrugLocationNotFound, DrugDetailNotFoundException {
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			MemberSubscription m = subscriptionClient.getSubscribeDetails(requestTokenHeader, order.getSid());
			DrugDetail d = drugClient.searchDrugByName(requestTokenHeader, m.getDrugName());
			DrugDetail d2 = drugClient.searchByLocationandDrugId(requestTokenHeader, d.getDrugId(), m.getMemberLocation());
			
			if(d2.getDrugLocationList().get(0).getQuantity() >= m.getQuantity())
			{
				RefillOrder rf = service.addRefillOrder(order);
				MyDate m2 = new MyDate();
				m2.setDate(rf.getRefillDate().toString());
				subscriptionClient.dateAndRefillOccuranceUpdate(requestTokenHeader, rf.getSubsId(), m2);
				return rf;
			}
			throw new DrugNotAvailableInLocationException("Quantity is not enough to refill");
		}
		else
		{
			throw new AuthorizationException("Not allowed");
		}
	}
	
	@GetMapping("/getthesubcriptionidshastorefill/{mid}")
	@ApiOperation(notes="Return the list of dues",value="View list of dues details")
	public List<DuesInformaton> getTheSubscriptionIdsHasToRefill(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("mid") String id) throws AuthorizationException
	{
		if(authorisingClient.authorizeTheRequest(requestTokenHeader))
		{
			return subscriptionClient.getTheListOfSubscriptionIdsDuesToRefill(requestTokenHeader,id);
		}
		else
		{
			throw new AuthorizationException("Not allowed");
		}
	}
	
	@GetMapping("/getrefillpaymentdues/{mid}")
	@ApiOperation(notes="Return the list of payment dues",value="View list of payment dues details")
	public List<RefillOrder> getRefillPaymentDues(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("mid") String id) throws AuthorizationException
	{
		if(authorisingClient.authorizeTheRequest(requestTokenHeader))
		{
			return service.getAllRefillswithDue(id);
		}
		else
		{
			throw new AuthorizationException("Not allowed");
		}
	}

	

}
