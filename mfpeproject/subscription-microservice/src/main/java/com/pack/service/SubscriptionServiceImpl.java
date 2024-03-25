package com.pack.service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pack.entity.MemberSubscription;
import com.pack.entity.SubsciptionStatus;
import com.pack.exception.MemberSubscriptionNotFoundException;
import com.pack.pojo.DuesInformaton;
import com.pack.pojo.MemberPrescriptionModel;
import com.pack.pojo.MyDate;
import com.pack.repository.MemberSubcriptionRepository;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	
	
	@Autowired
	MemberSubcriptionRepository memberSubcriptionRepository;
	
	@Override
	@Transactional
	public SubsciptionStatus subscribe(MemberPrescriptionModel me) {
		
		//List<MemberPrescription> list = memberPrescriptionRepository.findByMemberId(mid);
		
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		 

		  //convert String to LocalDate
		  
		
		MemberSubscription ms = MemberSubscription.builder()
				.date(LocalDate.parse(me.getDate(), formatter))
				.doctorName(me.getDoctorName())
				.drugName(me.getDrugName())
				.memberId(me.getMemberId())
				.memberLocation(me.getMemberLocation())
				.quantity(me.getQuantity())
				.refillOccurrence(0)
				.status(true)
				.course(me.getCourse())
				.build();
		
		
//		ms.setDate(LocalDate.parse(me.getDate(), formatter));
//		ms.setDoctorName(me.getDoctorName());
//		ms.setMemberId(me.getMemberId());
//		ms.setDrugName(me.getDrugName());
//		ms.setMemberLocation(me.getMemberLocation());
//		ms.setQuantity(me.getQuantity());
//		ms.setRefillOccurrence(10);
//		ms.setStatus(true);
//		ms.setCourse(me.getCourse());
		memberSubcriptionRepository.save(ms);
		SubsciptionStatus ss = SubsciptionStatus.builder()
				.isStatusYes(ms.isStatus())
				.statusDescription("Subscribed")
				.build();
//		ss.setStatusYes(ms.isStatus());
//		ss.setStatusDescription("Subscribed");
//		System.out.println(ms.getSubscriptionId());
//		System.out.println(ms);
		return ss;
		
		
	}

	@Override
	public MemberSubscription getSubscribeDetailsById(long id) throws MemberSubscriptionNotFoundException {

		MemberSubscription member = memberSubcriptionRepository.findById(id).orElse(null);
		if(member!=null) {
			return member;
		}
		throw new MemberSubscriptionNotFoundException("Member Subsciption not found");
	}

	@Override
	@Transactional
	public MemberSubscription updateDateAndRefillOccurance(long id, MyDate date) throws MemberSubscriptionNotFoundException {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
				MemberSubscription member = memberSubcriptionRepository.findById(id).orElse(null);
				//System.out.println(member);
				if(member!=null) {
					
					member.setDate(LocalDate.parse(date.getDate(), formatter));
					member.setRefillOccurrence(member.getRefillOccurrence() + 1);
					memberSubcriptionRepository.save(member);
					return member;
				}
				throw new MemberSubscriptionNotFoundException("Member Subsciption not found");

	}

	@Override
	public List<DuesInformaton> duesFucntion(String mid) {
	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		//DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd LLLL yyyy");
		
		LocalDate date = LocalDate.parse(LocalDate.now().format(formatter), formatter);
		
		List<MemberSubscription> mList = memberSubcriptionRepository.findAll();
		List<DuesInformaton> dList = new ArrayList<>();
		//long coursDays = 30;
		mList.stream().filter(m->m.isStatus() && m.getMemberId().equals(mid))
		.forEach(m->{
			long countDays = m.getDate().until(date, ChronoUnit.DAYS);
			if(m.getCourse().equalsIgnoreCase("weekly"))
			{
				
				if(countDays> 7)
				{
				    dList.add(new DuesInformaton(m.getSubscriptionId(), m.getMemberId(), countDays -7, m.getDrugName()));
				}
			}
			else
			{
				if(countDays > 30)
				{
					 dList.add(new DuesInformaton(m.getSubscriptionId(), m.getMemberId(), countDays -30, m.getDrugName()));
						
				}
			}
		});
//		for(MemberSubscription m :mList)
//		{
//			if(m.isStatus() == true) {
//			long countDays = m.getDate().until(date, ChronoUnit.DAYS);
//			if(m.getCourse().equalsIgnoreCase("weekly"))
//			{
//				
//				if(countDays> 7)
//				{
//				    dList.add(new DuesInformaton(m.getSubscriptionId(), m.getMemberId(), countDays -7, m.getDrugName()));
//				}
//			}
//			else
//			{
//				if(countDays > 30)
//				{
//					 dList.add(new DuesInformaton(m.getSubscriptionId(), m.getMemberId(), countDays -30, m.getDrugName()));
//						
//				}
//			}
//			}
//		}
		return dList;
	}

	@Override
	public SubsciptionStatus unsubscribeSubscriptionId(long id) throws MemberSubscriptionNotFoundException {

		MemberSubscription ms = memberSubcriptionRepository.findById(id).orElse(null);
		if(ms!=null) {
			ms.setStatus(false);
		memberSubcriptionRepository.save(ms);
		SubsciptionStatus ss = SubsciptionStatus.builder()
				.isStatusYes(false)
				.statusDescription("UnSubscribed")
				.build();
		return ss;
		}
		else
		throw new MemberSubscriptionNotFoundException("All ready unsubscribed...");
	}

	@Override
	public List<MemberSubscription> getAllSubscriptions(String mid) {
		 List<MemberSubscription> list = memberSubcriptionRepository.findAll();
		 List<MemberSubscription> list1=list.stream()
		 .filter(l->l.isStatus() && l.getMemberId().equals(mid))
		 .collect(Collectors.toList());
		return list1;
	}
	
	

}
