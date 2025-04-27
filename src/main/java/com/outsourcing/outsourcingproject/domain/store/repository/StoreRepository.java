package com.outsourcing.outsourcingproject.domain.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outsourcing.outsourcingproject.domain.store.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
	int countByUser_IdAndIsDeletedFalse(Long userId);

	Optional<Store> findById(Long id);

	// List<Store> findByName(String name);
	List<Store> findByNameAndIsDeletedFalse(String name);

}
