package com.outsourcing.outsourcingproject.domain.menu.entity;

import com.outsourcing.outsourcingproject.common.entity.BaseEntity;
import com.outsourcing.outsourcingproject.domain.store.entity.Store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "menu")// 뒤에 s 를 붙이는 경우는 예약어인 경우에만!! 예를들면 users 와같은거만
@NoArgsConstructor
public class Menu extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", nullable = false)
	private Store store;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private String menuType;

	@Column(nullable = false)
	private Boolean isDeleted = false;

	// id 제외한 생성자
	@Builder
	public Menu(Store store, String name, String content, int price, String menuType, Boolean isDeleted) {
		this.store = store;
		this.name = name;
		this.content = content;
		this.price = price;
		this.menuType = menuType;
		this.isDeleted = isDeleted;
	}

}
