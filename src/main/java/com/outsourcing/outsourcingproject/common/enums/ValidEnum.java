package com.outsourcing.outsourcingproject.common.enums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.outsourcing.outsourcingproject.common.util.EnumValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD}) // 어노테이션을 적용할 위치
@Retention(RetentionPolicy.RUNTIME) // 어노테이션 유지 범위 (RUNTIME - 실행하는 동안 유지)
@Constraint(validatedBy = EnumValidator.class) // 필드값을 검증할 검증 클래스
public @interface ValidEnum {
	String message() default "입력값이 유효하지 않습니다.";
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	Class<? extends Enum<?>> enumClass();
}
