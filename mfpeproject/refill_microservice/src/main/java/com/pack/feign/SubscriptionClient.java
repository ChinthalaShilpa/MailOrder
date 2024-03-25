package com.pack.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.pack.exception.AuthorizationException;
import com.pack.exception.MemberSubscriptionNotFoundException;
import com.pack.pojo.DuesInformaton;
import com.pack.pojo.MemberSubscription;
import com.pack.pojo.MyDate;

@FeignClient(name = "Subscription-microservice", url = "http://localhost:8200/subscription")
public interface SubscriptionClient {
	
	@GetMapping("/getsubscribebyid/{id}")
	public MemberSubscription getSubscribeDetails(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") long id) throws AuthorizationException, MemberSubscriptionNotFoundException;
	
	
	@PutMapping("/dateandrefilloccuranceupdate/{id}/{date}")
	public MemberSubscription dateAndRefillOccuranceUpdate(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, 
			@PathVariable("id") long id ,@RequestBody MyDate date) throws MemberSubscriptionNotFoundException, AuthorizationException;

	@GetMapping("/getListOfDues/{mid}")
	public List<DuesInformaton> getTheListOfSubscriptionIdsDuesToRefill(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("mid") String id) throws AuthorizationException;
	
}
