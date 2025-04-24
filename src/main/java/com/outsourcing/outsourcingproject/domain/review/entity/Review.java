package com.outsourcing.outsourcingproject.domain.review.entity;

import com.outsourcing.outsourcingproject.common.entity.BaseEntity;
import com.outsourcing.outsourcingproject.domain.order.entity.Order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "reviews")
@NoArgsConstructor
public class Review extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Double rating;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private Boolean isDeleted;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	public Review(Long id, Double rating, String title, String content, Boolean isDeleted, Order order) {
		this.id = id;
		this.rating = rating;
		this.title = title;
		this.content = content;
		this.isDeleted = isDeleted;
		this.order = order;
	}

	public Review(Double rating, String title, String content, Boolean isDeleted, Order order) {
		this.rating = rating;
		this.title = title;
		this.content = content;
		this.isDeleted = isDeleted;
		this.order = order;
	}
}
