package com.outsourcing.outsourcingproject.common.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.outsourcing.outsourcingproject.common.enums.ValidEnum;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum<?>> {
	private Set<String> values;

	/*
	검증 전에 values 필드에 enum의 이름을 미리 저장해두기 위한 메서드
	1. 커스텀 어노테이션이 적용된 Enum 클래스를 enumClass에 저장
	2. enum 요소를 values에 저장
	 */
	@Override
	public void initialize(ValidEnum validEnum) {
		Class<? extends Enum<?>> enumClass = validEnum.enumClass();

		values = Arrays.stream(enumClass.getEnumConstants())
			.map(Enum::name)
			.collect(Collectors.toSet());
	}

	// 전달된 입력값이 대문자로 변환한 값이 enum 이름과 동일한지 검증
	@Override
	public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
		return value != null && values.contains(value.name().toUpperCase());
	}
}
