package com.outsourcing.outsourcingproject.domain.order.entity;

import com.outsourcing.outsourcingproject.common.entity.BaseEntity;
import com.outsourcing.outsourcingproject.domain.menu.entity.Menu;
import com.outsourcing.outsourcingproject.domain.store.entity.Store;
import com.outsourcing.outsourcingproject.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
@Table(name = "orders")
@NoArgsConstructor
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	@ManyToOne(fetch = FetchType.LAZY) // order.getUser(); 할 때 쿼리 발생시키기 위함
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;

	/* ✏️
	@OneToOne = 식별자 To 식별자
		-> 하나의 Order 에 하나의 Menu 주문이 가능하다
		-> @OneToOne 을 쓰면 하나의 메뉴는 한 번 밖에 주문 못 한다 😅
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id")
	private Menu menu;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private DeliveryStatus deliveryStatus;

	public Order(User user, Store store, Menu menu, DeliveryStatus deliveryStatus) {
		this.user = user;
		this.store = store;
		this.menu = menu;
		this.deliveryStatus = deliveryStatus;
	}

	public void waiting() {
		this.deliveryStatus = DeliveryStatus.WAITING;
	}

	public void confirm() {
		this.deliveryStatus = DeliveryStatus.CONFIRM;
	}

	public void rejected() {
		this.deliveryStatus = DeliveryStatus.REJECTED;
	}
}
