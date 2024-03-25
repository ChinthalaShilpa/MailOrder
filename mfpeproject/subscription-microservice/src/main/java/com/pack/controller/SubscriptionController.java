package com.pack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.pack.entity.MemberSubscription;
import com.pack.entity.SubsciptionStatus;
import com.pack.exception.AuthorizationException;
import com.pack.exception.DrugDetailNotFoundException;
import com.pack.exception.DrugLocationNotFound;
import com.pack.exception.DrugNotAvailableInLocationException;
import com.pack.exception.DueIsFoundException;
import com.pack.exception.MemberSubscriptionNotFoundException;
import com.pack.feign.AuthorisingClient;
import com.pack.feign.DrugClient;
import com.pack.feign.RefillOrderClient;
import com.pack.pojo.DrugDetail;
import com.pack.pojo.DrugLocation;
import com.pack.pojo.DuesInformaton;
import com.pack.pojo.MemberPrescriptionModel;
import com.pack.pojo.MyDate;
import com.pack.pojo.RefillOrder;
import com.pack.service.SubscriptionService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
public class SubscriptionController {

	@Autowired
	AuthorisingClient authorisingClient;
	
	@Autowired
	DrugClient drugClient;
	
	@Autowired
	SubscriptionService service;
	
	@Autowired
	RefillOrderClient refillOrderClient;
	
	
	@GetMapping("/viewsubscription/{mid}")
	@ApiOperation(notes="view list of subscription by member",value="Get list of subscription")
	public List<MemberSubscription> getAllSubscriptions(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("mid") String mid) throws AuthorizationException{
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return service.getAllSubscriptions(mid);
		}
		else {
			throw new AuthorizationException("Not allowed");
		}
	}
	
	@GetMapping("/getsubscribebyid/{id}")
	@ApiOperation(notes="get subscription by id",value="Get subscription by id")
	public MemberSubscription getSubscribeDetails(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") long id) throws AuthorizationException, MemberSubscriptionNotFoundException {
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return service.getSubscribeDetailsById(id);
		}
		else {
			throw new AuthorizationException("Not allowed");
		}
	}
	
	@PostMapping("/subscribe")
	@ApiOperation(notes="view subscription status",value="subscribe to service")
	public SubsciptionStatus subscribe(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, 
			 @RequestBody MemberPrescriptionModel memberPrescription) throws AuthorizationException, DrugDetailNotFoundException, DrugLocationNotFound, DrugNotAvailableInLocationException
	{
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			
			DrugDetail d = drugClient.searchDrugByName(requestTokenHeader,memberPrescription.getDrugName());
			if(d!=null)
			{
			    for(DrugLocation loc : d.getDrugLocationList())
			    {
			    	if(loc.getLocation().equalsIgnoreCase(memberPrescription.getMemberLocation()))
			    	{
			    		 if(loc.getQuantity()>= memberPrescription.getQuantity())
					    			{
					    		     return service.subscribe(memberPrescription);
					    			}
			    		throw new DrugNotAvailableInLocationException("Drug is not enough");
			     	}
			    }
			    throw new DrugLocationNotFound("Location not found");
			}
			throw new DrugDetailNotFoundException("Drug is not available");
		} else {
			throw new AuthorizationException("Not allowed");
		}
		
		
	
	}
	
	
	@PutMapping("/dateandrefilloccuranceupdate/{mid}")
	@ApiOperation(notes="view date and refill occurance update",value="Get date and refill occurance update by id")
	public MemberSubscription dateAndRefillOccuranceUpdate(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, 
			@PathVariable("mid") long id ,@RequestBody MyDate date) throws MemberSubscriptionNotFoundException, AuthorizationException
	{
		if(authorisingClient.authorizeTheRequest(requestTokenHeader))
		{
			return service.updateDateAndRefillOccurance(id, date);
		}
		else {
			throw new AuthorizationException("Not allowed");
		}
		
	}
	
	@GetMapping("/getListOfDues/{mid}")
	@ApiOperation(notes="view list of dues",value="Get the of payment dues")
	public List<DuesInformaton> getTheListOfSubscriptionIdsDuesToRefill(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("mid") String id) throws AuthorizationException
	{
		if(authorisingClient.authorizeTheRequest(requestTokenHeader))
		{
			return service.duesFucntion(id);
		}
		else {
			throw new AuthorizationException("Not allowed");
		}
	}
	
	@GetMapping("/unsubscribe/{id}")
	@ApiOperation(notes="view Unsubscribe status",value="unsubscribe the service")
	public SubsciptionStatus  unsubscribeSubscription(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, @PathVariable("id") long id) throws AuthorizationException, MemberSubscriptionNotFoundException, DueIsFoundException
	{
		if(authorisingClient.authorizeTheRequest(requestTokenHeader))
		{
			RefillOrder order=refillOrderClient.getRefillStatus(requestTokenHeader, id);
			if(order!=null && order.isPaymentStatus())
			{
				return service.unsubscribeSubscriptionId(id);	
			}
			else
				throw new DueIsFoundException("Due is found. Can not be unsubscribed");
		}
		else {
			throw new AuthorizationException("Not allowed");
		}
	}

}
