package ru.social_network.users.annotaion;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Аннотация, предназначенная для исключения методов из Jacoco отчетов
 */

@Retention(RUNTIME)
@Target({TYPE, METHOD, CONSTRUCTOR})
public @interface ExcludeFromJacocoGeneratedReport {
}
