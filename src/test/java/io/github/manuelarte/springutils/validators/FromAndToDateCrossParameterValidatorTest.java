package io.github.manuelarte.springutils.validators;

import io.github.manuelarte.springutils.constraints.FromAndToDate;
import jakarta.validation.ConstraintTarget;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import jakarta.validation.groups.Default;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FromAndToDateCrossParameterValidatorTest {

  @Test
  void testFromAndToAreEqualsDateAndValid() {
    final FromToDateCrossParameterValidator fromToDateCrossParameterValidator = new FromToDateCrossParameterValidator();
    fromToDateCrossParameterValidator.initialize(createFromToDate(FromAndToDate.FromToType.FROM_LOWER_THAN_OR_EQUAL_TO_TO));
    final Object[] value = new Object[]{ new Date(), new Date() };
    assertTrue(fromToDateCrossParameterValidator.isValid(value, mock(
        ConstraintValidatorContext.class)));
  }

  @Test
  void testFromAndToAreEqualsDateAndInvalid() {
    final FromToDateCrossParameterValidator fromToDateCrossParameterValidator = new FromToDateCrossParameterValidator();
    fromToDateCrossParameterValidator.initialize(createFromToDate(FromAndToDate.FromToType.FROM_LOWER_THAN_TO));
    final Object[] value = new Object[]{ new Date(), new Date() };
    assertFalse(fromToDateCrossParameterValidator.isValid(value, mock(
        ConstraintValidatorContext.class)));
  }

  @Test
  void testFromAndToAreDatesAndToIsLower() {
    final FromToDateCrossParameterValidator fromToDateCrossParameterValidator = new FromToDateCrossParameterValidator();
    fromToDateCrossParameterValidator.initialize(createFromToDate(FromAndToDate.FromToType.FROM_LOWER_THAN_OR_EQUAL_TO_TO));
    final Date from = new Date();
    final Date to = new Date(new Date().getTime() - 1000);
    final Object[] value = new Object[]{ from, to };
    assertFalse(fromToDateCrossParameterValidator.isValid(value, mock(
        ConstraintValidatorContext.class)));
  }

  @SuppressWarnings({"UnusedMethod", "JavaTimeDefaultTimeZone"})
  private static Stream<Arguments> isValidTestDataProvider() {
    return Stream.of(
        Arguments.of(createFromToDate(FromAndToDate.FromToType.FROM_LOWER_THAN_OR_EQUAL_TO_TO),
            new Date(), new Date(new Date().getTime() + 1000), true),
        Arguments.of(createFromToDate(FromAndToDate.FromToType.FROM_LOWER_THAN_OR_EQUAL_TO_TO),
            LocalDate.now(), LocalDate.now().plusDays(1), true)
    );
  }

  @ParameterizedTest
  @MethodSource("isValidTestDataProvider")
  void testIsValid(final FromAndToDate annotation, final Object from, final Object to, boolean expected) {
    final FromToDateCrossParameterValidator fromToDateCrossParameterValidator = new FromToDateCrossParameterValidator();
    fromToDateCrossParameterValidator.initialize(annotation);
    final Object[] value = new Object[]{ from, to };
    assertEquals(expected, fromToDateCrossParameterValidator.isValid(value, mock(
        ConstraintValidatorContext.class)));
  }

  @Test
  void testNotExpectedValues() {
    final FromToDateCrossParameterValidator fromToDateCrossParameterValidator = new FromToDateCrossParameterValidator();
    fromToDateCrossParameterValidator.initialize(createFromToDate(FromAndToDate.FromToType.FROM_LOWER_THAN_OR_EQUAL_TO_TO));
    final Object[] value = new Object[]{ "hola", "adios" };
    assertFalse(fromToDateCrossParameterValidator.isValid(value, mock(ConstraintValidatorContext.class)));
  }

  private static FromAndToDate createFromToDate(final FromAndToDate.FromToType value) {
    return new FromAndToDate() {

      @Override
      public Class<? extends Annotation> annotationType() {
        return null;
      }

      @Override
      public FromToType value() {
        return value;
      }

      @Override
      public String[] identifiers() {
        return new String[0];
      }

      @Override
      public int[] paramIndexes() {
        return new int[] {0, 1};
      }

      @Override
      public String message() {
        return null;
      }

      @Override
      public Class<?>[] groups() {
        return new Class[]{ Default.class };
      }

      @Override
      public Class<? extends Payload>[] payload() {
        return new Class[0];
      }

      @Override
      public ConstraintTarget validationAppliesTo() {
        return ConstraintTarget.IMPLICIT;
      }
    };
  }

}
