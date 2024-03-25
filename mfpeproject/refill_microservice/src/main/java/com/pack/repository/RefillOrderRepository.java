package com.pack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.pack.entity.RefillOrder;

@Repository
public interface RefillOrderRepository extends JpaRepository<RefillOrder, Integer>{
	
//	@Query(value="select r from RefillOrder r where r.sId=?1 orderby r.refillDate desc limit 1",nativeQuery=true)
	
	//@Query(value="select * from (select * from refill_order r order by r.refill_date where a.s_id = :id order by r.refill_date desc) a where a.ROW_NUM = 1", nativeQuery=true)
	//RefillOrder findTop1BySubsIdOrderByRefillDateDesc(long id);
	RefillOrder findTop1BySubsIdOrderByRefillOrderIdDesc(long id);
	List<RefillOrder> findBySubsId(long id);
	
	
}
