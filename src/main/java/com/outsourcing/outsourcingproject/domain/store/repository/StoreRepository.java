package com.outsourcing.outsourcingproject.domain.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.outsourcing.outsourcingproject.domain.store.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
	int countByUser_IdAndIsDeletedFalse(Long userId);

	Optional<Store> findById(Long id);
	// findBy 할때마다 status 변경하는 법?

	// store 객체가 생성될 때 마다 status setting 값을 바꿔주어야 함

	List<Store> findByNameAndIsDeletedFalse(String name);

	@Modifying
	@Query("UPDATE Store s SET s.reviewCounts = s.reviewCounts+1 WHERE s.id = :storeId")
	void increaseReviewCounts(Long storeId);

	@Modifying
	@Query("UPDATE Store s SET s.reviewCounts = s.reviewCounts-1 WHERE s.id = :storeId")
	void decreaseReviewCounts(Long storeId);

}
