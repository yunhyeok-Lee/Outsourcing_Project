package com.outsourcing.outsourcingproject.domain.review.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

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
@SQLRestriction("is_deleted is false")
@SQLDelete(sql = "UPDATE REVIEWS SET REVIEWS.is_deleted = true where REVIEWS.review_id = ?")
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

	public Review(Double rating, String title, String content, Order order) {
		this.rating = rating;
		this.title = title;
		this.content = content;
		this.order = order;
	}

	// 이 친구들 생성자 아니라 메서드임! void 붙여줘야 다른곳에서 쓸 수 있음
	public void UpdateContent(String content) {
		this.content = content;
	}

	public void UpdateRaing(Double rating) {
		this.rating = rating;
	}

	public void UpdateTitle(String title) {
		this.title = title;
	}
}
