package com.outsourcing.outsourcingproject.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outsourcing.outsourcingproject.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
