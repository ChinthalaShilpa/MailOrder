package com.pack.service;

import java.util.List;

import com.pack.entity.DrugDetail;
import com.pack.exception.DrugDetailNotFoundException;
import com.pack.exception.DrugLocationNotFound;

public interface DrugService {

	public DrugDetail getDrugById(String id) throws DrugDetailNotFoundException;

	public DrugDetail getDrugByName(String name) throws DrugDetailNotFoundException;

	public DrugDetail searchByLocationandDrugId(String id, String location) throws DrugLocationNotFound, DrugDetailNotFoundException;

	public List<DrugDetail> getListOfDrugDetail();
}
