package com.outsourcing.outsourcingproject.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outsourcing.outsourcingproject.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findUserByEmail(String email);

	Optional<User> findUserByEmailAndIsDeleted(String email, boolean deleted);

	Optional<User> findUserById(Long id);
}
