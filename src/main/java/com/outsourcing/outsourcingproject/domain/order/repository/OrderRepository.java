package com.outsourcing.outsourcingproject.domain.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outsourcing.outsourcingproject.domain.order.entity.DeliveryStatus;
import com.outsourcing.outsourcingproject.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByOrderIdAndStatus(Long orderId, DeliveryStatus deliveryStatus);

	Optional<Order> findOrderByUserId(Long orderId, Long userId);

}
