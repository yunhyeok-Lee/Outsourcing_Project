package com.outsourcing.outsourcingproject.common.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.outsourcing.outsourcingproject.common.enums.ValidEnum;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
	private Set<String> values;
	private String message;

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

		this.message = validEnum.message();
	}

	/*
	입력값이 null, "", " "인 경우는 검증 통과
	입력값을 대문자로 변환했을 때 values에 포함된 enum 요소와 동일하다면 검증 통과
	그 외의 경우는 검증 실패로 에러 메시지 응답
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isBlank(value) || values.contains(value.toUpperCase())) {
			return true;
		}

		return false;
	}
}
