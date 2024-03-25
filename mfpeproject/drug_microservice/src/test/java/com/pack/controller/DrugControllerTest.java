package com.pack.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pack.entity.DrugDetail;
import com.pack.entity.DrugLocation;
import com.pack.entity.Stock;
import com.pack.feign.AuthorisingClient;
import com.pack.service.DrugService;

@AutoConfigureMockMvc
@SpringBootTest
public class DrugControllerTest {
	

	@InjectMocks
	DrugController drugController;

	@MockBean
	DrugService drugService;

	@MockBean
	AuthorisingClient client;
	
	@Autowired
	MockMvc mockMvc;

	DrugDetail drugDetail1;
	DrugDetail drugDetail2;
	Stock stock;
	List<DrugLocation> list = new ArrayList<>();
	
	@BeforeEach
	public void setup() {
		list.add(new DrugLocation(1, "Chennai", 30));
		list.add(new DrugLocation(2, "Bangalore", 50));
		
		
		drugDetail1 = new DrugDetail("PR1", "Paracetamol", "cysco", toConvert("22-22-2020"), toConvert("22-11-2024"),list);
		drugDetail2 = new DrugDetail("CR1", "Crocin", "cipla", toConvert("22-22-2020"), toConvert("22-11-2024"),list);
		stock = new Stock("CR1", "Crocin", toConvert("22-11-2024"),30);
		
	}

	@Test
	void testGetDrugByIdWithToken() throws Exception {
		
		when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		when(drugService.getDrugById("PR1")).thenReturn(drugDetail1);	
		mockMvc.perform(get("/searchdrugbyid/PR1").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isOk());
	}
	@Test
    void testGetDrugByIdFalseToken() throws Exception {
		
		when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
		mockMvc.perform(get("/searchdrugbyid/PR1").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isForbidden());
	}
	@Test
    void testGetDrugByIdWithoutToken() throws Exception {
		
		this.mockMvc.perform(get("/searchdrugbyid/PR1")).andExpect(status().isBadRequest());
	}
	

	@Test
	void testGetDrugByNameWithToken() throws Exception {
		
		when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		when(drugService.getDrugByName("Paracetamol")).thenReturn(drugDetail1);	
		mockMvc.perform(get("/searchdrugbyname/Paracetamol").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isOk());
	}
	@Test
    void testGetDrugByNameWithFalseToken() throws Exception {
		
		when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
		mockMvc.perform(get("/searchdrugbyname/Paracetamol").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isForbidden());
		}
	@Test
    void testGetDrugByNameWithoutToken() throws Exception {
		
		this.mockMvc.perform(get("/searchdrugbyname/Paracetamol")).andExpect(status().isBadRequest());
	}
	

	@Test
	void testGetDrugByIdAndLocationWithToken() throws Exception {
		
		when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		when(drugService.searchByLocationandDrugId("PR1", "Chennai")).thenReturn(drugDetail1);	
		mockMvc.perform(get("/searchbylocationanddrugid/PR1/Chennai").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isOk());
	}
	@Test
    void testGetDrugByDrugIdAndLocationWithFalseToken() throws Exception {
		
		when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
		mockMvc.perform(get("/searchbylocationanddrugid/PR1/Chennai").header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isForbidden());
		}
	@Test
    void testGetDrugByDrugIdAndLocationWithoutToken() throws Exception {
		
		this.mockMvc.perform(get("/searchbylocationanddrugid/PR1/Chennai")).andExpect(status().isBadRequest());
	}
	
	
	@Test
	void testgetAllDrugDetailsWithValidToken() throws Exception {
		when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		when(drugService.getListOfDrugDetail()).thenReturn(Arrays.asList(drugDetail1));
		this.mockMvc.perform(get("/viewdrugs").header("Authorization","@uthoriz@tionToken123")).andExpect(status().isOk());
	}
	
	
	@Test
	void testgetAllDrugDetailswithInvalidToken() throws Exception {
		when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
		//when(drugService.getListOfDrugDetail()).thenReturn(Arrays.asList(drugDetail1));
		this.mockMvc.perform(get("/viewdrugs").header("Authorization","@uthoriz@tionToken123")).andExpect(status().isForbidden());
	}
	
	@Test
	void testgetAllDrugDetailswithoutToken() throws Exception {
		//when(client.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(false);
		//when(drugService.getListOfDrugDetail()).thenReturn(Arrays.asList(drugDetail1));
		this.mockMvc.perform(get("/viewdrugs")).andExpect(status().isBadRequest());
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
