package com.outsourcing.outsourcingproject.domain.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.outsourcing.outsourcingproject.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	boolean existsByOrder_Id(Long orderId);

	Page<Review> findByStoreId(Long storeId, Pageable pageable);

	boolean existsByStore_Id(Long storeId);

	boolean existsByParent(Review review);
}
