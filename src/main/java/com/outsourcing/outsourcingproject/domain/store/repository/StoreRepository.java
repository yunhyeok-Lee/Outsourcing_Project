package com.outsourcing.outsourcingproject.domain.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.outsourcing.outsourcingproject.domain.store.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
