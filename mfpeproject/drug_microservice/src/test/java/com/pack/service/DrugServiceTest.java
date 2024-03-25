package com.pack.service;

import static org.junit.Assert.assertThrows;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.pack.entity.DrugDetail;
import com.pack.entity.DrugLocation;
import com.pack.entity.Stock;
import com.pack.exception.DrugDetailNotFoundException;
import com.pack.exception.DrugLocationNotFound;

import com.pack.repository.DrugDetailRepository;


@SpringBootTest
public class DrugServiceTest {

	@InjectMocks
	DrugServiceImpl drugService;
		
	
	@Mock
	DrugDetailRepository drugDetailRepository;
	
	
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
	
	}
	
	
	@Test
	@DisplayName("Test getDrugById() of DrugService")
	public void testValidgetDrugById() throws DrugDetailNotFoundException {
	
		when(drugDetailRepository.findById("PR1")).thenReturn(Optional.of(drugDetail1));
		assertEquals(drugDetail1, drugService.getDrugById("PR1"));
	}
	
	@Test
	@DisplayName("Test getDrugById() of DrugService with Exception")
	public void testValidgetDrugByIdWithException() throws DrugDetailNotFoundException {
	
		//when(drugDetailRepository.findById("CR2")).thenReturn(Optional.of());
		assertThrows(DrugDetailNotFoundException.class, () ->{
		drugService.getDrugById("CR0");
		});
	}
	
	@Test
	@DisplayName("Test getDrugByName() of DrugService")
	public void testGetByName() throws DrugDetailNotFoundException {
		when(drugDetailRepository.findByDrugName("Paracetamol")).thenReturn(Optional.of(drugDetail1));
		assertEquals(drugDetail1, drugService.getDrugByName("Paracetamol"));
	}
	
	@Test
	@DisplayName("Test getDrugByName() of DrugService with Exception")
	public void testValidgetDrugByNameWithException() throws DrugDetailNotFoundException {
		
		//when(drugDetailRepository.findById("CR2")).thenReturn(Optional.of());
		assertThrows(DrugDetailNotFoundException.class, () ->{
		drugService.getDrugByName("thylma");
		});
	}
	
	@Test
	@DisplayName("Test GetByLocationAndId of DrugService")
	public void testGetByLocationAndId() throws DrugDetailNotFoundException, DrugLocationNotFound {
		when(drugDetailRepository.findById("CR1")).thenReturn(Optional.of(drugDetail2));
		//when(drugDetailRepository.findByDrugName("Paracetamol")).thenReturn(Optional.of(drugDetail1));
		// DrugLocation loc = new DrugLocation(1, "Chennai", 30);
		//when(loc.getLocation().equals("Chennai")).thenReturn(true);
		assertEquals(drugDetail2, drugService.searchByLocationandDrugId("CR1", "Chennai"));
	}
	
	@Test
	@DisplayName("Test GetByLocationAndId of DrugService with Exception")
	public void testGetByLocationAndIdWithException1() throws DrugDetailNotFoundException {
	
		//when(drugDetailRepository.findById("CR2")).thenReturn(Optional.of());
		assertThrows(DrugDetailNotFoundException.class, () ->{
		drugService.searchByLocationandDrugId("CR0", "Chennai");
		});
	}
	
	@Test
	@DisplayName("Test GetByLocationAndId of DrugService with Location Exception")
	public void testGetByLocationAndIdWithException2() throws DrugLocationNotFound {
		when(drugDetailRepository.findById("CR1")).thenReturn(Optional.of(drugDetail2));
		assertThrows(DrugLocationNotFound.class, () ->{
		drugService.searchByLocationandDrugId("CR1", "Banga");
		});
	}
	
	
	@Test
	@DisplayName("Test GetAllDrugDetails of DrugService")
	public void testGetAllDrugDetails() {
		when(drugDetailRepository.findAll()).thenReturn(Arrays.asList(drugDetail1));
		assertEquals(1,drugService.getListOfDrugDetail().size());
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