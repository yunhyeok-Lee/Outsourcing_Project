package com.outsourcing.outsourcingproject.domain.review.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.outsourcing.outsourcingproject.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	// boolean existsByOrderId(Long orderId);

	// Page<Review> findByStoreId(Long storeId, Pageable pageable);

	boolean existsByParent(Review review);

	@Query("SELECT r FROM Review r " +
		"WHERE r.order.id IN :orderIds " +
		"AND r.isDeleted = false " +
		"AND (:minRating IS NULL OR r.rating >= :minRating) " +
		"AND (:maxRating IS NULL OR r.rating <= :maxRating)")
	Page<Review> findReviewsByOrderIdsWithRatingFilter(
		@Param("orderIds") List<Long> orderIds,
		@Param("minRating") Integer minRating,
		@Param("maxRating") Integer maxRating,
		Pageable pageable
	);

	boolean existsByOrder_Id(Long orderId);

	// findbyid 메서드 사용 위치 별로 구분
	// (N+1 문제 해결 위해 EntityGraph 사용할건데
	// 매번 모든 테이블 조인한 채로 가져오면 결국 즉시 Join과 다를 게 없으므로
	// 사용 용도에 맞게 테이블을 각각 조인한 메서드로 분리)
	// OrElse 처리

	// 0. findById <- join x (default findById 메서드)
	// 1. findByIdforUser <- user join (review 수정, 삭제)
	// 2. findByIdforStore <- store join (reviewcounts 증감)
	// 3. findByIdforOwnwer <- store.user join (가게 사장 ID 뽑아오기, 이중 join)

}
