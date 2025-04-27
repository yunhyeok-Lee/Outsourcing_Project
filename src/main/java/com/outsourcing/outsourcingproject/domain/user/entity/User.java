package com.outsourcing.outsourcingproject.domain.user.entity;

import org.hibernate.annotations.DynamicUpdate;

import com.outsourcing.outsourcingproject.common.entity.BaseEntity;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "users")
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	private String address;

	@Enumerated(EnumType.STRING)//enum 사용시 꼭 필요
	@Column(nullable = false)
	private Authority authority;

	@Column(nullable = false)
	private boolean isDeleted = false;

	@Builder
	public User(String email, String password, String nickname, String phoneNumber, String address,
		String authority) {
		this.email = email;

		this.password = password;

		this.nickname = StringUtils.isBlank(nickname)
			? "익명의 사용자"
			: nickname;

		this.phoneNumber = phoneNumber;

		this.address = address;

		this.authority = StringUtils.isBlank(authority)
			? Authority.USER
			: Authority.valueOf(authority.toUpperCase());
	}

	public void updateDeletedStatus() {
		this.isDeleted = true;
	}

	public void updateUserInfo(String nickname, String password, String address) {
		if (!StringUtils.isBlank(nickname)) {
			this.nickname = nickname;
		}

		if (!StringUtils.isBlank(password)) {
			this.password = password;
		}

		if (!StringUtils.isBlank(address)) {
			this.address = address;
		}
	}
}
