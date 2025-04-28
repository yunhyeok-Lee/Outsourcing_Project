package com.outsourcing.outsourcingproject.domain.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.outsourcing.outsourcingproject.domain.order.entity.DeliveryStatus;
import com.outsourcing.outsourcingproject.domain.order.entity.Order;
import com.outsourcing.outsourcingproject.domain.store.entity.Store;

public interface OrderRepository extends JpaRepository<Order, Long> {

	Optional<Order> findOrderByUserId(Long userId);

	List<Order> findAllByStore(Store store);

	@Query("SELECT o.id FROM Order o WHERE o.store.id = :storeId")
	List<Long> findOrderIdsByStoreId(Long storeId);
}
