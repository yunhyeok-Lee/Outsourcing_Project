package com.outsourcing.outsourcingproject.domain.store.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;

public class StoreTest {
	@Test
	void 잘못된_시간_문자열이면_예외가_발생한다() {
		// given
		String invalidTimeString = "14시30분";

		// when & then
		assertThatThrownBy(() -> TimeUtil.toLocalTime(invalidTimeString))
			.isInstanceOf(CustomException.class)
			.hasMessageContaining(ErrorCode.INVALID_TIME_FORMAT.getMessage());

	}
}
