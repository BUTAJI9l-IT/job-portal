package com.github.butaji9l.jobportal.be.utils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.AnnotatedElementUtils;

/**
 * Utility class for helping process class fields with {@link java.lang.reflect}.
 *
 * @author Vitalii Bortsov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClassFieldsUtils {

  public static boolean applyToAnnotatedFieldsWithValuesPresent(Object obj,
    Class<? extends Annotation> annotationClass,
    Consumer<Field> action) {
    final var filtered = Arrays.stream(obj.getClass().getDeclaredFields())
      .filter(f -> isAnnotationPresent(f, annotationClass) && fieldValuePresent(obj, f)).toList();
    if (filtered.isEmpty()) {
      return false;
    }
    filtered.forEach(action);
    return true;
  }

  public static boolean isAnnotationPresent(Field field,
    Class<? extends Annotation> annotationClass) {
    return findAnnotation(field, annotationClass) != null;
  }

  public static <T extends Annotation> T findAnnotation(Field field, Class<T> annotationClass) {
    return AnnotatedElementUtils.findMergedAnnotation(field, annotationClass);
  }

  public static boolean isCollectionType(Field field) {
    return Collection.class.isAssignableFrom(field.getType());
  }

  public static <T> Object getFieldValue(Field field, T object) {
    try {
      return new PropertyDescriptor(field.getName(), object.getClass()).getReadMethod()
        .invoke(object);
    } catch (Exception e) {
      return null;
    }
  }

  private static boolean fieldValuePresent(Object obj, Field f) {
    final var value = getFieldValue(f, obj);
    if (value instanceof Collection<?> collection) {
      return !collection.isEmpty();
    }
    return value != null;
  }
}
