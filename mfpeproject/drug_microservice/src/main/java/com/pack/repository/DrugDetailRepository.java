package com.pack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pack.entity.DrugDetail;

@Repository
public interface DrugDetailRepository extends JpaRepository<DrugDetail, String> {

	Optional<DrugDetail> findByDrugName(String name);
}
