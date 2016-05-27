package com.tcp.chr.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  AccessRole Annotation
 * 
 *  접근 권한을 명시하는 어노테이션이다.
 * 
 *  @date 2016.05.17
 *  @author genie
 *  @since 1.0
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessRole {

	Role role();
	
	enum Role {
		/**
		 *  시스템 관리자
		 */
		admin,
		
		/**
		 *  상관없음
		 */
		all,
		
		/**
		 *  소유자 
		 */
		author,
		
		/**
		 *  선택적
		 *  
		 *  비 로그인상태는 0, 이외에는 사용자고유번호
		 */
		selector
	}
}
