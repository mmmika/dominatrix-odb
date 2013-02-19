package net.onedaybeard.artemis;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to rename components when marshalling to json.
 */
@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.TYPE) @Documented
public @interface JsonComponentAlias
{
	String value();
}
