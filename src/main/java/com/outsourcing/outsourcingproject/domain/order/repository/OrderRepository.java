package com.outsourcing.outsourcingproject.domain.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outsourcing.outsourcingproject.domain.order.entity.DeliveryStatus;
import com.outsourcing.outsourcingproject.domain.order.entity.Order;
import com.outsourcing.outsourcingproject.domain.store.entity.Store;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByIdAndDeliveryStatus(Long id, DeliveryStatus deliveryStatus);

	Optional<Order> findOrderByUserId(Long userId);

	List<Order> findAllByStore(Store store);

}
