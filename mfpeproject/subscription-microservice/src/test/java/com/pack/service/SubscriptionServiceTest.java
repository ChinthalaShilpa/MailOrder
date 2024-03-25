package com.pack.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.pack.entity.MemberSubscription;
import com.pack.entity.SubsciptionStatus;
import com.pack.exception.MemberSubscriptionNotFoundException;
import com.pack.pojo.DuesInformaton;
import com.pack.pojo.MemberPrescriptionModel;
import com.pack.pojo.MyDate;
import com.pack.repository.MemberSubcriptionRepository;

@SpringBootTest
public class SubscriptionServiceTest {
	
	@InjectMocks
	SubscriptionServiceImpl subscriptionservice;
	
	@Mock
	MemberSubcriptionRepository memberSubcriptionRepository;
	
	
	
	MemberSubscription membersubcription1;
	
	MemberPrescriptionModel me;
	
	SubsciptionStatus ss;
	MemberSubscription ms;
	
	MemberSubscription ms2;
	SubsciptionStatus ss2;
	MemberSubscription ms3;
	MemberSubscription ms4;
	MemberSubscription ms5;
	List<MemberSubscription> mList = new ArrayList<>();
	DuesInformaton d1;
	DuesInformaton d2;
	List<DuesInformaton> dList = new ArrayList<>();
	@BeforeEach
	public void setup() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		me=new MemberPrescriptionModel("mg01","chennai","10/03/2022",45,"paracetamol","kishore","weekly");
		
		ms2=new MemberSubscription(2l, "mg02","mumbai",LocalDate.parse("28/02/2022", formatter),45,"","kishore kumar",10, true, "weekly");
		ms3=new MemberSubscription(3l, "mg03","mumbai",LocalDate.parse("03/02/2022", formatter),45,"paracetamol","kishore kumar",10, true, "monthly");
		ms4 = new MemberSubscription(4l, "mg03","mumbai",LocalDate.parse("03/03/2022", formatter),45,"paracetamol","kishore kumar",10, true, "monthly");
		ms5 = new MemberSubscription(5l, "mg03","mumbai",LocalDate.parse("03/03/2022", formatter),45,"paracetamol","kishore kumar",10, false, "monthly");
		
		d1 = new DuesInformaton(2l, "mg02", 7, "paracetamol");
		d2 = new DuesInformaton(3l, "mg03", 9, "paracetamol");
		ms = new MemberSubscription();
		ms.setSubscriptionId(1);
		ms.setDate(LocalDate.parse(me.getDate(), formatter));
		ms.setDoctorName(me.getDoctorName());
		ms.setMemberId(me.getMemberId());
		ms.setDrugName(me.getDrugName());
		ms.setMemberLocation(me.getMemberLocation());
		ms.setQuantity(me.getQuantity());
		ms.setCourse(me.getCourse());
		ms.setStatus(true);
		ss=new SubsciptionStatus(true,"Subscribed");
		ss2 = new SubsciptionStatus(false,"UnSubscribed");
		
		
	}
	
	@Test
	//@DisplayName("Test findIPTreatmentPackageByName() of IPTreatmentPackageService")
	public void testSubscribe() {
		when(memberSubcriptionRepository.save(ms)).thenReturn(ms);
		assertEquals(ss,subscriptionservice.subscribe(me));
	}
	
	@Test
	public void testgetsubscriberbyid() throws MemberSubscriptionNotFoundException {
		when(memberSubcriptionRepository.findById(1l)).thenReturn(Optional.of(ms));
		assertEquals(ms,subscriptionservice.getSubscribeDetailsById(1l));
	}	
	
	@Test
	public void testgetsubscriberbyidwithexception() throws MemberSubscriptionNotFoundException {
		assertThrows(MemberSubscriptionNotFoundException.class,()->{
		subscriptionservice.getSubscribeDetailsById(2l);
		});
	}
	@Test
	public void unsubscribeSubscriptionIdTest() throws MemberSubscriptionNotFoundException {
		when(memberSubcriptionRepository.findById(1l)).thenReturn(Optional.of(ms));
		when(memberSubcriptionRepository.save(ms)).thenReturn(ms);
		assertEquals(ss2, subscriptionservice.unsubscribeSubscriptionId(1l));
	}

	@Test
	public void unsubscribeSubscriptionIdTestWithException() throws MemberSubscriptionNotFoundException {
		assertThrows(MemberSubscriptionNotFoundException.class,()->{
		subscriptionservice.unsubscribeSubscriptionId(2l);
		});
	}

	
	@Test
	public void  updateDateAndRefillOccuranceTest() throws MemberSubscriptionNotFoundException
	{
		when(memberSubcriptionRepository.findById(1l)).thenReturn(Optional.of(ms));
		when(memberSubcriptionRepository.save(ms)).thenReturn(ms);
		assertEquals(ms, subscriptionservice.updateDateAndRefillOccurance(1l, new MyDate("2021-02-02")));

	}
	
	
	
	@Test
	public void updateDateAndRefillOccuranceTestWithException() throws MemberSubscriptionNotFoundException {
		assertThrows(MemberSubscriptionNotFoundException.class,()->{
		subscriptionservice.updateDateAndRefillOccurance(2l, new MyDate("2021-02-02"));
		});
	}
	
	@Test
	public void duesFucntionTest()
	{
		mList.add(ms2);
		dList.add(d1);
		when(memberSubcriptionRepository.findAll()).thenReturn(mList);
		when(subscriptionservice.duesFucntion("mg02")).thenReturn(dList);
		
		
	}
	@Test
	public void duesFucntionTest3()
	{
		mList.add(ms);
		when(memberSubcriptionRepository.findAll()).thenReturn(mList);
		assertEquals(0,subscriptionservice.duesFucntion("mg03").size());
		
		
	}
	@Test
	public void duesFucntionTest5()
	{
		mList.add(ms5);
		when(memberSubcriptionRepository.findAll()).thenReturn(mList);
		assertEquals(0,subscriptionservice.duesFucntion("mg04").size());
		
		
	}
	@Test
	public void duesFucntionTest4()
	{
		mList.add(ms4);
		when(memberSubcriptionRepository.findAll()).thenReturn(mList);
		assertEquals(0,subscriptionservice.duesFucntion("mg05").size());
		
		
	}
	@Test
	public void duesFucntionTest2()
	{
		mList.add(ms3);
		dList.add(d2);
		when(memberSubcriptionRepository.findAll()).thenReturn(mList);
		when(subscriptionservice.duesFucntion("mg03")).thenReturn(dList);
	}
	
	@Test
	public void duesFucntionTestWhenStatusIsTrue2()
	{
		dList.add(d1);
		//dList.add(d2);
		when(memberSubcriptionRepository.findAll()).thenReturn(mList);
		when(subscriptionservice.duesFucntion("mg02")).thenReturn(dList);
	}
	
	@Test
	public void testGetAllSubscriptionss() {
		when(memberSubcriptionRepository.findAll()).thenReturn(Arrays.asList(ms));
		assertEquals(1,subscriptionservice.getAllSubscriptions("mg01").size());
	}
	
	
}