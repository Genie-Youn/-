package com.tcp.chr.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * API Annotation
 * 
 * API의 명세를 나타내는 어노테이션이다.
 * 
 * @date 2016.05.17
 * @author genie
 * @since 1.0
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface API {
	Useage value();
	
	enum Useage {
		
		/**
		 *  내부용
		 */
		Internal,
		
		/**
		 *  안정
		 */
		Stable,
		
		/**
		 *  사용중단
		 */
		Deprecated,
		
		/**
		 *  개발중
		 */
		Experimental
		
	}
}
