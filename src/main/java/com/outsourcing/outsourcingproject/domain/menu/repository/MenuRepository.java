package com.outsourcing.outsourcingproject.domain.menu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outsourcing.outsourcingproject.domain.menu.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	List<Menu> findByStore_Id(Long id);
}
