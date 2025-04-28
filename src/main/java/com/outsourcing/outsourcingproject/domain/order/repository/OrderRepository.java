package com.outsourcing.outsourcingproject.domain.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.outsourcing.outsourcingproject.domain.order.entity.DeliveryStatus;
import com.outsourcing.outsourcingproject.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByIdAndDeliveryStatus(Long id, DeliveryStatus deliveryStatus);

	Optional<Order> findOrderByUserId(Long userId);

	@Query("SELECT o.id FROM Order o WHERE o.store.id = :storeId")
	List<Long> findOrderIdsByStoreId(Long storeId);
}
