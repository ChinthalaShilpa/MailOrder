package com.pack.service;

import java.util.List;

import com.pack.entity.RefillOrder;
import com.pack.exception.DueIsFoundException;
import com.pack.exception.MemberSubscriptionNotFoundException;
import com.pack.pojo.MemberSubscription;
import com.pack.pojo.RefillPojo;

public interface RefillMicroservice {

	RefillOrder getRefillStatus(MemberSubscription member) throws DueIsFoundException, MemberSubscriptionNotFoundException;

	RefillOrder addRefillOrder(RefillPojo order);

	List<RefillOrder> getAllRefills(String mid);
	
	List<RefillOrder> getAllRefillswithDue(String mid);

	

}
