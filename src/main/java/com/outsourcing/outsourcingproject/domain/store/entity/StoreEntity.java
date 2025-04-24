package com.outsourcing.outsourcingproject.domain.store.entity;

import java.awt.*;
import java.time.LocalTime;

import com.outsourcing.outsourcingproject.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "stores")
@NoArgsConstructor
public class StoreEntity extends BaseEntity {
	//id identity 설정
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//null 값 넣을 수 없도록 제한
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String status;

	@Column(nullable = false)
	private LocalTime openTime;

	@Column(nullable = false)
	private LocalTime closeTime;

	@Column(nullable = false)
	private int minOrderAmount;

	@Column(nullable = false)
	private String address;
	//isDeleted의 디폴트 값 false
	@Column(nullable = false)
	private Boolean isDeleted = false;

	//user 테이블과 다대일 연관관계 설정
	//LAZY 로딩 사용, 반드시 user_id와 맵핑되어야 함
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	//menu 테이블과 일대다 연관관계 설정
	@OneToMany(mappedBy = "store")
	private List<Menu> menus;
	//
	// @OneToMany(mappedBy = "store")
	// private List<Order> orders;

}
