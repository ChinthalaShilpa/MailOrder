package com.pack.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.pack.entity.RefillOrder;
import com.pack.exception.DueIsFoundException;
import com.pack.exception.MemberSubscriptionNotFoundException;
import com.pack.pojo.MemberSubscription;
import com.pack.pojo.RefillPojo;
import com.pack.repository.RefillOrderRepository;

@SpringBootTest
public class RefillMicroserviceImplTest {

	@InjectMocks
	RefillMicroserviceImpl service;
	
	@Mock
	RefillOrderRepository orderRepository;
	
	List<RefillOrder> rList;
	RefillOrder refillOrder1;
	RefillOrder refillOrder2;
	MemberSubscription ms2;
	MemberSubscription ms3;
	RefillPojo rp;
	@BeforeEach
	public void setup() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		ms2=new MemberSubscription(1, "mg02","mumbai",LocalDate.parse("28/02/2022", formatter),45,"","kishore kumar",10, true, "weekly");
		ms3=new MemberSubscription(2, "mg02","mumbai",LocalDate.parse("28/02/2022", formatter),45,"","kishore kumar",10, true, "weekly");
		rp = new RefillPojo(true,"mg02",1);
		refillOrder1 = new RefillOrder(0,LocalDate.now() ,"mg02", 1, true);
		refillOrder2 = new RefillOrder(31,LocalDate.parse("27/02/2022", formatter) ,"mg02", 2, false);
		rList = new ArrayList<>();
		
	}
	
	
	@Test
	public void getRefillStatusTest() throws DueIsFoundException, MemberSubscriptionNotFoundException {
		rList.add(refillOrder1);
	
		when(orderRepository.findBySubsId(1l)).thenReturn(rList);
		when(orderRepository.findTop1BySubsIdOrderByRefillOrderIdDesc(1)).thenReturn(refillOrder1);
		assertEquals(refillOrder1, service.getRefillStatus(ms2));
	}
	@Test
	public void getRefillStatusTest2() throws DueIsFoundException, MemberSubscriptionNotFoundException {
		rList.add(refillOrder2);
	
		when(orderRepository.findBySubsId(2)).thenReturn(rList);
     	when(orderRepository.findTop1BySubsIdOrderByRefillOrderIdDesc(2)).thenReturn(refillOrder2);
//		assertEquals(refillOrder1, service.getRefillStatus(ms2));
		assertThrows(DueIsFoundException.class,()->{
			service.getRefillStatus(ms3);
			});
	}
	
	@Test
	public void getRefillStatusTest3() throws DueIsFoundException, MemberSubscriptionNotFoundException{
		assertThrows(MemberSubscriptionNotFoundException.class,()->{
			service.getRefillStatus(ms3);
			});
	}
	
	@Test
	public void addRefillOrderTest() {
		when(orderRepository.save(refillOrder1)).thenReturn(refillOrder1);
		assertEquals(refillOrder1, service.addRefillOrder(rp));
	}
	
	@Test
	public void getAllRefillOrdersTest() {
		rList.add(refillOrder2);
		when(orderRepository.findAll()).thenReturn(rList);
		assertEquals(1, service.getAllRefills("mg02").size());
	}
	
	@Test
	public void getAllRefillDueOrdersTest() {
		rList.add(refillOrder2);
		when(orderRepository.findAll()).thenReturn(rList);
		assertEquals(1, service.getAllRefillswithDue("mg02").size());
	}
	
	@Test
	public void getAllRefillDueOrdersTest1() {
		rList.add(refillOrder1);
		when(orderRepository.findAll()).thenReturn(rList);
		assertEquals(0, service.getAllRefillswithDue("mg02").size());
	}
}
