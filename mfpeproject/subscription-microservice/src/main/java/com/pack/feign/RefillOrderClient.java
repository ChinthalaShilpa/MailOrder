package com.pack.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


import com.pack.exception.AuthorizationException;
import com.pack.exception.DueIsFoundException;
import com.pack.exception.MemberSubscriptionNotFoundException;
import com.pack.pojo.RefillOrder;

@FeignClient(name = "Refill-Microservice", url = "http://localhost:8300/refill")
public interface RefillOrderClient {

	@GetMapping("/viewrefillstatus/{id}")
	public RefillOrder getRefillStatus(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") long id ) throws AuthorizationException, MemberSubscriptionNotFoundException, DueIsFoundException;
	
}
