package com.pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pack.entity.MemberSubscription;

@Repository
public interface MemberSubcriptionRepository extends JpaRepository<MemberSubscription, Long> {

}
