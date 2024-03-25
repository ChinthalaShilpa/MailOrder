package com.pack.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pack.entity.MemberSubscription;
import com.pack.entity.SubsciptionStatus;
import com.pack.exception.AuthorizationException;
import com.pack.exception.DrugDetailNotFoundException;
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

@AutoConfigureMockMvc

@SpringBootTest
public class SubscriptionControllerTest {
	
	@InjectMocks
	SubscriptionController subcriptioncontroller;
	@MockBean
	AuthorisingClient authorisingClient;
	@MockBean
	DrugClient drugClient;
	@MockBean
	SubscriptionService service;
	@MockBean
	RefillOrderClient refillOrderClient;
	
	@Autowired
	private MockMvc mockMvc;
	
MemberSubscription membersubcription1;
	
	MemberPrescriptionModel me;
	MemberPrescriptionModel me1;
	MemberPrescriptionModel me2;
	
	SubsciptionStatus ss;
	MemberSubscription ms;
	RefillOrder refillOrder1;
	RefillOrder refillOrder2;
	
	MemberSubscription ms2;
	SubsciptionStatus ss2;
	MemberSubscription ms3;
	MemberSubscription ms4;
	MemberSubscription ms5;
	List<MemberSubscription> mList = new ArrayList<>();
	DuesInformaton d1;
	DuesInformaton d2;
	List<DuesInformaton> dList = new ArrayList<>();
	DrugDetail drugDetail;
	DrugDetail drugDetail1;
	List<DrugLocation> list = new ArrayList<>();
	
	@BeforeEach
	public void setup() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		me=new MemberPrescriptionModel("mg01","chennai","10/03/2022",45,"paracetamol","kishore","weekly");
		list.add(new DrugLocation(1, "Chennai", 100));
		list.add(new DrugLocation(2, "Bangalore", 10));
		me1=new MemberPrescriptionModel("mg01","Bangalore","10/03/2022",45,"paracetamol","kishore","weekly");
		me2=new MemberPrescriptionModel("mg01","Bangalore","10/03/2022",45,"crocin","kishore","weekly");
		refillOrder1 = new RefillOrder(0,LocalDate.now() , 2, true);
		refillOrder2 = new RefillOrder(31,LocalDate.parse("27/02/2022", formatter) , 2, false);
		ms2=new MemberSubscription(2l, "mg02","mumbai",LocalDate.parse("28/02/2022", formatter),45,"","kishore kumar",10, true, "weekly");
		ms3=new MemberSubscription(3l, "mg03","mumbai",LocalDate.parse("03/02/2022", formatter),45,"paracetamol","kishore kumar",10, true, "monthly");
		ms4=new MemberSubscription(2, "mg02","Chennai",LocalDate.parse("15/03/2022", formatter),20,"","kishore kumar",11, true, "weekly");
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
		drugDetail = new DrugDetail("CR1", "paracetamol", "cipla", toConvert("22-22-2020"), toConvert("22-11-2024"),list);
		drugDetail1 = new DrugDetail("CR1", "paracetamol", "cipla", toConvert("22-22-2020"), toConvert("22-11-2024"),new ArrayList<>());
			}
	
	@Test
	public void getSubscribeDetailsTestwithtoken() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		when(service.getSubscribeDetailsById(2)).thenReturn(ms2);
		mockMvc.perform(get("/getsubscribebyid/2").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isOk());
	}
	
	@Test
	public void getSubscribeDetailsTestwithInvalidtoken() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
		//when(service.getSubscribeDetailsById(2)).thenReturn(ms2);
		mockMvc.perform(get("/getsubscribebyid/2").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isForbidden());
	}
	
	@Test
	public void getSubscribeDetailsTestwithouttoken() throws Exception {
		//when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
		//when(service.getSubscribeDetailsById(2)).thenReturn(ms2);
		mockMvc.perform(get("/getsubscribebyid/2")).andExpect(status().isBadRequest());
	}
	
	@Test
	public void subscribeTestWithToken() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		when(drugClient.searchDrugByName("@uthoriz@tionToken123","paracetamol")).thenReturn(drugDetail);
		when(service.subscribe(me)).thenReturn(ss);
		//mockMvc.perform(post("/subscribe").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isOk());
		this.mockMvc.perform(post("/subscribe").header("Authorization", "@uthoriz@tionToken123")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(me)))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void subscribeTestWithInvalidToken() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
		//when(drugClient.searchDrugByName("@uthoriz@tionToken123","paracetamol")).thenReturn(drugDetail);
		//when(service.subscribe(me)).thenReturn(ss);
		//mockMvc.perform(post("/subscribe").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isOk());
		this.mockMvc.perform(post("/subscribe").header("Authorization", "@uthoriz@tionToken123")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(me)))
				.andExpect(status().isForbidden());
	}
	
	@Test
	public void subscribeTestWithoutToken() throws Exception {
		//when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
		//when(drugClient.searchDrugByName("@uthoriz@tionToken123","paracetamol")).thenReturn(drugDetail);
		//when(service.subscribe(me)).thenReturn(ss);
		//mockMvc.perform(post("/subscribe").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isOk());
		this.mockMvc.perform(post("/subscribe")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(me)))
				.andExpect(status().isBadRequest());
	}
	public static String asJsonString(final Object obj) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
		}
	
	@Test
	public void subscribeTestWithTokenwithException() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		when(drugClient.searchDrugByName("@uthoriz@tionToken123","paracetamol")).thenReturn(drugDetail);
		when(service.subscribe(me1)).thenReturn(ss);
		//mockMvc.perform(post("/subscribe").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isOk());
		this.mockMvc.perform(post("/subscribe").header("Authorization", "@uthoriz@tionToken123")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(me1)))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void subscribeTestWithTokenwithException2() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		//when(drugClient.searchDrugByName("@uthoriz@tionToken123","paracetamol")).thenReturn(drugDetail);
//		//when(service.subscribe(me1)).thenReturn(ss);
//		//mockMvc.perform(post("/subscribe").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isOk());
		this.mockMvc.perform(post("/subscribe").header("Authorization", "@uthoriz@tionToken123")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(me2))).andExpect(status().isNotFound());
	}
//	
	
	@Test
	public void subscribeTestWithTokenwithException3() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		when(drugClient.searchDrugByName("@uthoriz@tionToken123","paracetamol")).thenReturn(drugDetail1);
//		//when(service.subscribe(me1)).thenReturn(ss);
//		//mockMvc.perform(post("/subscribe").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isOk());
		this.mockMvc.perform(post("/subscribe").header("Authorization", "@uthoriz@tionToken123")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(me1))).andExpect(status().isNotFound());
	}
//	
	@Test
	public void getTheListOfSubscriptionIdsDuesToRefillwithToken() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		dList.add(d1);
		when( service.duesFucntion("mg02")).thenReturn(dList);
		mockMvc.perform(get("/getListOfDues/mg02").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isOk());
		
		
	}
	
	@Test
	public void getTheListOfSubscriptionIdsDuesToRefillwithInvalidToken() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
		//dList.add(d1);
		//when( service.duesFucntion()).thenReturn(dList);
		mockMvc.perform(get("/getListOfDues/mg02").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isForbidden());
		
		
	}
	
	@Test
	public void getTheListOfSubscriptionIdsDuesToRefillwithoutToken() throws Exception {
		//when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		//dList.add(d1);
		//when( service.duesFucntion()).thenReturn(dList);
		mockMvc.perform(get("/getListOfDues/mg02")).andExpect(status().isBadRequest());
		
		
	}
	
	@Test 
	public void dateAndRefillOccuranceUpdatewithToken() throws JsonProcessingException, Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		when(service.updateDateAndRefillOccurance(2, new MyDate("15/03/2022"))).thenReturn(ms4);
		this.mockMvc.perform(put("/dateandrefilloccuranceupdate/2").header("Authorization", "@uthoriz@tionToken123")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new MyDate("15/03/2022"))))
				.andExpect(status().isOk());
		
		
	 }
	
	@Test 
	public void dateAndRefillOccuranceUpdatewithInvalidToken() throws JsonProcessingException, Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
		//when(service.updateDateAndRefillOccurance(2, new MyDate("15/03/2022"))).thenReturn(ms4);
		this.mockMvc.perform(put("/dateandrefilloccuranceupdate/2").header("Authorization", "@uthoriz@tionToken123")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new MyDate("15/03/2022"))))
				.andExpect(status().isForbidden());
		
		
	 }
	
	@Test 
	public void dateAndRefillOccuranceUpdatewithoutToken() throws JsonProcessingException, Exception {
		//when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		//when(service.updateDateAndRefillOccurance(2, new MyDate("15/03/2022"))).thenReturn(ms4);
		this.mockMvc.perform(put("/dateandrefilloccuranceupdate/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new MyDate("15/03/2022"))))
				.andExpect(status().isBadRequest());
		
		
	 }
	
	@Test
	public void unsubscribeSubscriptionTestWithToken() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		when(refillOrderClient.getRefillStatus("@uthoriz@tionToken123", 2)).thenReturn(refillOrder1);
		when(service.unsubscribeSubscriptionId(2)).thenReturn(ss2);
		this.mockMvc.perform(get("/unsubscribe/2").header("Authorization", "@uthoriz@tionToken123"))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void unsubscribeSubscriptionTestWithInvalidToken() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
		//when(refillOrderClient.getRefillStatus("@uthoriz@tionToken123", 2).isPaymentStatus()).thenReturn(true);
		//when(service.unsubscribeSubscriptionId(2)).thenReturn(ss2);
		this.mockMvc.perform(get("/unsubscribe/2").header("Authorization", "@uthoriz@tionToken123"))
				.andExpect(status().isForbidden());
		
	}
	
	@Test
	public void unsubscribeSubscriptionTestWithoutToken() throws Exception {
		//when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		//when(refillOrderClient.getRefillStatus("@uthoriz@tionToken123", 2).isPaymentStatus()).thenReturn(true);
		//when(service.unsubscribeSubscriptionId(2)).thenReturn(ss2);
		this.mockMvc.perform(get("/unsubscribe/2"))
				.andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void unsubscribeSubscriptionTestWithTokenwithException() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		when(refillOrderClient.getRefillStatus("@uthoriz@tionToken123", 2)).thenReturn(refillOrder2);
		//when(service.unsubscribeSubscriptionId(2)).thenReturn(ss2);
		this.mockMvc.perform(get("/unsubscribe/2").header("Authorization", "@uthoriz@tionToken123"))
				.andExpect(status().isFound());
	}
	
	@Test
	public void unsubscribeSubscriptionTestWithTokenwithException1() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		when(refillOrderClient.getRefillStatus("@uthoriz@tionToken123", 2)).thenReturn(null);
		//when(service.unsubscribeSubscriptionId(2)).thenReturn(ss2);
		this.mockMvc.perform(get("/unsubscribe/2").header("Authorization", "@uthoriz@tionToken123"))
				.andExpect(status().isFound());
	}
	
	
	
	
	@Test
	void testgetAllSubscriptionsWithValidToken() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		when(service.getAllSubscriptions("mg01")).thenReturn(Arrays.asList(ms));
		this.mockMvc.perform(get("/viewsubscription/mg01").header("Authorization","@uthoriz@tionToken123")).andExpect(status().isOk());
	}
	
	
	@Test
	void testgetAllSubscriptionswithInvalidToken() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
		//when(drugService.getListOfDrugDetail()).thenReturn(Arrays.asList(drugDetail1));
		this.mockMvc.perform(get("/viewsubscription/mg02").header("Authorization","@uthoriz@tionToken123")).andExpect(status().isForbidden());
	}
	
	@Test
	void testgetAllSubscriptionswithoutToken() throws Exception {
		//when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
		//when(drugService.getListOfDrugDetail()).thenReturn(Arrays.asList(drugDetail1));
		this.mockMvc.perform(get("/viewsubscription/mg02")).andExpect(status().isBadRequest());
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
