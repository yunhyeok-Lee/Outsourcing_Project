package com.outsourcing.outsourcingproject.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outsourcing.outsourcingproject.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
