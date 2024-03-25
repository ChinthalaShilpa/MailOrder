package com.pack.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pack.entity.RefillOrder;
import com.pack.exception.DueIsFoundException;
import com.pack.exception.MemberSubscriptionNotFoundException;
import com.pack.pojo.MemberSubscription;
import com.pack.pojo.RefillPojo;
import com.pack.repository.RefillOrderRepository;

@Service
public class RefillMicroserviceImpl implements RefillMicroservice {
	
	@Autowired
	RefillOrderRepository orderRepository;
	
	 

	@Override
	public RefillOrder getRefillStatus(MemberSubscription member) throws DueIsFoundException, MemberSubscriptionNotFoundException {
		List<RefillOrder> rList = orderRepository.findBySubsId(member.getSubscriptionId());
		if(rList.size() > 0) {
			RefillOrder order = orderRepository.findTop1BySubsIdOrderByRefillOrderIdDesc(member.getSubscriptionId());
			if(order.isPaymentStatus()) {
				
				return order;
			}
			throw new DueIsFoundException("due of payment not done");
		}
		else
		{
			throw new MemberSubscriptionNotFoundException("Subscription id not available in refill Order list");
		}
	}

	@Override
	public RefillOrder addRefillOrder(RefillPojo order) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(LocalDate.now().format(formatter), formatter);
		RefillOrder refill = RefillOrder.builder()
				.paymentStatus(order.isStatusOk())
				.memberId(order.getMid())
				.refillDate(date)
				.subsId(order.getSid())
				.build();
		orderRepository.save(refill);
		return refill;
	}

	@Override
	public List<RefillOrder> getAllRefills(String mid) {
		List<RefillOrder> list = orderRepository.findAll();
		return list.stream()
		.filter(m->m.getMemberId().equals(mid))
		.collect(Collectors.toList());
		
	}

	@Override
	public List<RefillOrder> getAllRefillswithDue(String mid) {
		List<RefillOrder> list = orderRepository.findAll();
		return list.stream()
		.filter(m->m.getMemberId().equals(mid) && m.isPaymentStatus() == false)
		.collect(Collectors.toList());
	}

	

	

}
