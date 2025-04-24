package com.outsourcing.outsourcingproject.domain.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outsourcing.outsourcingproject.domain.store.entity.Store;
import com.outsourcing.outsourcingproject.domain.user.entity.User;

public interface StoreRepository extends JpaRepository<Store, Long> {
	int countByUserAndIsDeletedFalse(User user);
}
