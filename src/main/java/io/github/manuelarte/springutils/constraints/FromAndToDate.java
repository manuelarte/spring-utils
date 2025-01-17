package io.github.manuelarte.springutils.constraints;

import io.github.manuelarte.springutils.validators.FromToDateCrossParameterValidator;
import io.github.manuelarte.springutils.validators.FromToDateTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintTarget;
import jakarta.validation.Payload;


import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { FromToDateTypeValidator.class, FromToDateCrossParameterValidator.class })
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(FromAndToDate.List.class)
public @interface FromAndToDate {

  enum FromToType {
    FROM_LOWER_THAN_TO, FROM_LOWER_THAN_OR_EQUAL_TO_TO
  }

  FromToType value() default FromToType.FROM_LOWER_THAN_TO;

  String[] identifiers() default {};

  /**
   * The param index from the from and to
   * @return The indexes of the two dates to check
   */
  int[] paramIndexes() default {0, 1};

  String message() default "From date and to date not being honored";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  ConstraintTarget validationAppliesTo() default ConstraintTarget.IMPLICIT;

  @Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @interface List {
    FromAndToDate[] value();
  }

}
