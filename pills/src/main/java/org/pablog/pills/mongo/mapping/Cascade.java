package org.pablog.pills.mongo.mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Cascade {
	/**
	 * Defines cascade operations on referred entity
	 *
	 * @return
	 */
	CascadeType cascadeType() default CascadeType.NONE;
}
