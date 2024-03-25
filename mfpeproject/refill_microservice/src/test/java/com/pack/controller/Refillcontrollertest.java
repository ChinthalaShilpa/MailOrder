package com.pack.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pack.entity.RefillOrder;
import com.pack.feign.AuthorisingClient;
import com.pack.feign.DrugClient;
import com.pack.feign.SubscriptionClient;
import com.pack.pojo.DrugDetail;
import com.pack.pojo.DrugLocation;
import com.pack.pojo.DuesInformaton;
import com.pack.pojo.MemberSubscription;
import com.pack.pojo.MyDate;
import com.pack.pojo.RefillPojo;
import com.pack.pojo.Stock;
import com.pack.service.RefillMicroserviceImpl;

@AutoConfigureMockMvc

@SpringBootTest
public class Refillcontrollertest {
	@InjectMocks
	private RefillController refillController;
	@MockBean
	private AuthorisingClient client;
	@MockBean
	private SubscriptionClient subscriptionClient;
	@MockBean
	private DrugClient drugClient;
	@MockBean
	private RefillMicroserviceImpl refillService;
	@Autowired
	private MockMvc mockMvc;
	private RefillOrder refillOrder1;
	private RefillOrder refillOrder2;
	private MemberSubscription ms2;
	private MemberSubscription ms3;
	private MemberSubscription ms4;
	private RefillPojo rp;
	private DrugDetail drugDetail2;
	private Stock stock;
	private List<DrugLocation> list = new ArrayList<>();
	private List<DuesInformaton> list1;
	DuesInformaton d1;
	@BeforeEach
	public void setup() {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	list.add(new DrugLocation(1, "Chennai", 100));
	list.add(new DrugLocation(2, "Bangalore", 100));
	ms2=new MemberSubscription(1, "mg02","Chennai",LocalDate.parse("28/02/2022", formatter),45,"","kishore kumar",10, true, "weekly");
	ms3=new MemberSubscription(2, "mg02","Chennai",LocalDate.parse("28/02/2022", formatter),20,"","kishore kumar",10, true, "weekly");
	ms4=new MemberSubscription(2, "mg02","Chennai",LocalDate.parse("15/03/2022", formatter),20,"","kishore kumar",11, true, "weekly");
	rp = new RefillPojo(true,"mg02",2);
	refillOrder1 = new RefillOrder(0,LocalDate.now() ,"mg02", 2, true);
	refillOrder2 = new RefillOrder(31,LocalDate.parse("27/02/2022", formatter) ,"mg02", 2, true);
	drugDetail2 = new DrugDetail("CR1", "paracetamol", "cipla", toConvert("22-22-2020"), toConvert("22-11-2024"),list);
	stock = new Stock("CR1", "paracetamol", toConvert("22-11-2024"),30);
	list1=new ArrayList<>();
	d1 = new DuesInformaton(2, "mg02", 7, "paracetamol");

	}
	@Test
	public void getRefillStatusTestwithToken() throws Exception
	{
	when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
	when(subscriptionClient.getSubscribeDetails("@uthoriz@tionToken123", 2)).thenReturn(ms3);
	when(refillService.getRefillStatus(ms3)).thenReturn(refillOrder2);
	mockMvc.perform(get("/viewrefillstatus/2").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isOk());
	}
	@Test
	public void getRefillStatusTestFalseToken() throws Exception
	{
	when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
	//when(subscriptionClient.getSubscribeDetails("@uthoriz@tionToken123", 2)).thenReturn(ms3);
	//when(refillService.getRefillStatus(ms3)).thenReturn(refillOrder2);
	mockMvc.perform(get("/viewrefillstatus/2").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isForbidden());
	}
	@Test
	public void getRefillStatusTestwithTokenWithException2() throws Exception
	{
	when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
	//when(subscriptionClient.getSubscribeDetails("@uthoriz@tionToken123", 2)).thenReturn(ms3);
	//when(refillService.getRefillStatus(ms3)).thenReturn(refillOrder2);
	mockMvc.perform(get("/viewrefillstatus/2").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isNotFound());
	}
	@Test
	public void getRefillStatusWithoutToken() throws Exception
	{
	mockMvc.perform(get("/viewrefillstatus/2")).andExpect(status().isBadRequest());
	}
	
	@Test
	public void addRefillOrderTestWithToken() throws Exception
	{
	when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
	when(subscriptionClient.getSubscribeDetails("@uthoriz@tionToken123", 2)).thenReturn(ms3);
	when(drugClient.searchDrugByName("@uthoriz@tionToken123", "paracetamol")).thenReturn(drugDetail2);
	when(drugClient.searchByLocationandDrugId("@uthoriz@tionToken123", "CR1", "Chennai")).thenReturn(drugDetail2);
	when(refillService.addRefillOrder(rp)).thenReturn(refillOrder2);
	when(subscriptionClient.dateAndRefillOccuranceUpdate("@uthoriz@tionToken123", 2, new MyDate("15/03/2022"))).thenReturn(ms4);
	//mockMvc.perform(post("/requestadhocrefill").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isOk());
	this.mockMvc.perform(post("/requestadhocrefill").header("Authorization", "@uthoriz@tionToken123")
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(rp)))
			.andExpect(status().isOk());
	}
	public static String asJsonString(final Object obj) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
		}

	
	@Test
	public void addRefillTestFalseToken() throws Exception
	{
	when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
	//when(subscriptionClient.getSubscribeDetails("@uthoriz@tionToken123", 2)).thenReturn(ms3);
	//when(refillService.getRefillStatus(ms3)).thenReturn(refillOrder2);
	mockMvc.perform(post("/requestadhocrefill").header("Authorization", "@uthoriz@tionToken123").contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(rp))).andExpect(status().isForbidden());
	}
	
	@Test
	public void addRefillTestWithoutToken() throws Exception
	{
	//when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
	//when(subscriptionClient.getSubscribeDetails("@uthoriz@tionToken123", 2)).thenReturn(ms3);
	//when(refillService.getRefillStatus(ms3)).thenReturn(refillOrder2);
	mockMvc.perform(post("/requestadhocrefill").contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(rp))).andExpect(status().isBadRequest());
	}
	
	@Test
	public void getthesubcriptionidshastorefilltest() throws Exception
	{
	when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
	list1.add(d1);
	when(subscriptionClient.getTheListOfSubscriptionIdsDuesToRefill("@uthoriz@tionToken123","mg02")).thenReturn(list1);
	mockMvc.perform(get("/getthesubcriptionidshastorefill/mg02").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isOk());
	}
	
	@Test
	public void getthesubcriptionidshastorefilltestinvalidtoken() throws Exception
	{
	when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
	mockMvc.perform(get("/getthesubcriptionidshastorefill/mg02").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isForbidden());
	}
	
	@Test
	public void getthesubcriptionidshastorefilltestwithouttoken() throws Exception
	{
	mockMvc.perform(get("/getthesubcriptionidshastorefill/mg02")).andExpect(status().isBadRequest());
	}
	
	@Test
	public void getAllRefillListTest() throws Exception {
		when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		when(refillService.getAllRefills("mg02")).thenReturn(Arrays.asList(refillOrder2));
		mockMvc.perform(get("/getrefillorders/mg02").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isOk());
	}
	
	
	@Test
	public void getAllRefillListTestWithInvalidToken() throws Exception {
		when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
		//when(refillService.getAllRefills("mg02")).thenReturn(Arrays.asList(refillOrder2));
		mockMvc.perform(get("/getrefillorders/mg02").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isForbidden());
	}
	
	@Test
	public void getAllRefillListTestWithoutToken() throws Exception {
		//when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
		//when(refillService.getAllRefills("mg02")).thenReturn(Arrays.asList(refillOrder2));
		mockMvc.perform(get("/getrefillorders/mg02")).andExpect(status().isBadRequest());
	}
	
	@Test
	public void getAllRefillDueListTest() throws Exception {
		when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		when(refillService.getAllRefills("mg02")).thenReturn(Arrays.asList(refillOrder2));
		mockMvc.perform(get("/getrefillpaymentdues/mg02").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isOk());
	}
	
	
	@Test
	public void getAllRefillDueListTestWithInvalidToken() throws Exception {
		when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
		//when(refillService.getAllRefills("mg02")).thenReturn(Arrays.asList(refillOrder2));
		mockMvc.perform(get("/getrefillpaymentdues/mg02").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isForbidden());
	}
	
	@Test
	public void getAllRefillDueListTestWithoutToken() throws Exception {
		//when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
		//when(refillService.getAllRefills("mg02")).thenReturn(Arrays.asList(refillOrder2));
		mockMvc.perform(get("/getrefillpaymentdues/mg02")).andExpect(status().isBadRequest());
	}
	
	
	public Date toConvert(String date) {
		Date dateFormatter = null;
		try {
		dateFormatter = new SimpleDateFormat("dd-MM-yyyy").parse(date);
		} catch (ParseException e) {
		e.printStackTrace();
		}
		return dateFormatter;
		}
	}
