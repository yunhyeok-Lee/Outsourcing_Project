package com.outsourcing.outsourcingproject.domain.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outsourcing.outsourcingproject.domain.store.entity.Store;
import com.outsourcing.outsourcingproject.domain.user.entity.User;

public interface StoreRepository extends JpaRepository<Store, Long> {
	int countByUserAndIsDeletedFalse(User user);

	Optional<Store> findById(Long id);

	List<Store> findByName(String name);
}
