package com.pack.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pack.entity.DrugDetail;
import com.pack.entity.DrugLocation;
import com.pack.exception.DrugDetailNotFoundException;
import com.pack.exception.DrugLocationNotFound;
import com.pack.repository.DrugDetailRepository;

@Service
public class DrugServiceImpl implements DrugService {

	
	@Autowired
	DrugDetailRepository drugDetailRepository;
	
	
	@Override
	public DrugDetail getDrugById(String id) throws DrugDetailNotFoundException {
		
		DrugDetail drugDetail = drugDetailRepository.findById(id).orElse(null);
		if(drugDetail!=null) {
			return drugDetail;
		}
		else
			throw new DrugDetailNotFoundException("Drug is not available");
	}


	@Override
	public DrugDetail getDrugByName(String name) throws DrugDetailNotFoundException {
		DrugDetail drugDetail = drugDetailRepository.findByDrugName(name).orElse(null);
		if(drugDetail!=null)
			return drugDetail;
		else
			throw new DrugDetailNotFoundException("Drug is not available");
		
	}


	@Override
	public DrugDetail searchByLocationandDrugId(String id, String location) throws DrugLocationNotFound, DrugDetailNotFoundException {
		DrugDetail drugDetail = drugDetailRepository.findById(id).orElse(null);
		if(drugDetail!=null)
		{
			List<DrugLocation> locationList = drugDetail.getDrugLocationList();
			Integer quantity= locationList.stream()
			.filter(loc->loc.getLocation().equals(location))
			.map(drug->drug.getQuantity())
			.reduce(0,(a,b)->a+b);
			
			if(quantity>0) {
				drugDetail.setDrugLocationList(Arrays.asList(new DrugLocation(1,location,quantity)));
				return drugDetail;
			}
			throw new DrugLocationNotFound("Drug location is not found with drug id");
			
		}
		else
			throw new DrugDetailNotFoundException("Drug is not available");
		
	}


	@Override
	public List<DrugDetail> getListOfDrugDetail() {
		List<DrugDetail> list = drugDetailRepository.findAll();
		return list;
	}

	
}
