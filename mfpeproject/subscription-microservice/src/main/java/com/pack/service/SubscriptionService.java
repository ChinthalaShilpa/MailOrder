package com.pack.service;



import java.util.List;

import com.pack.pojo.DuesInformaton;
import com.pack.entity.MemberSubscription;
import com.pack.entity.SubsciptionStatus;
import com.pack.exception.MemberSubscriptionNotFoundException;
import com.pack.pojo.MemberPrescriptionModel;
import com.pack.pojo.MyDate;

public interface SubscriptionService {

	public SubsciptionStatus subscribe(MemberPrescriptionModel memberPrescription);

	public MemberSubscription getSubscribeDetailsById(long id) throws MemberSubscriptionNotFoundException;

	public MemberSubscription updateDateAndRefillOccurance(long id, MyDate date) throws MemberSubscriptionNotFoundException;

	public List<DuesInformaton> duesFucntion(String id);

	public SubsciptionStatus unsubscribeSubscriptionId(long id) throws MemberSubscriptionNotFoundException;

	public List<MemberSubscription> getAllSubscriptions(String mid);
	
}
