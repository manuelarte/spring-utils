package io.github.manuelarte.springutils.validators;

import io.github.manuelarte.springutils.constraints.FromAndToDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.time.temporal.Temporal;
import java.util.Date;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@lombok.RequiredArgsConstructor
public class FromToDateCrossParameterValidator implements ConstraintValidator<FromAndToDate, Object[]> {

  private FromAndToDate.FromToType fromToType;
  private int[] paramIndex;

  @Override
  public void initialize(final FromAndToDate constraintAnnotation) {
    this.fromToType = constraintAnnotation.value();
    this.paramIndex = constraintAnnotation.paramIndexes();
  }

  @Override
  public boolean isValid(final Object[] value, final ConstraintValidatorContext context) {
    if (this.paramIndex.length != 2) {
      context.buildConstraintViolationWithTemplate("param index has to be size 2");
      return false;
    }
    if ( value.length != 2 || value.length < paramIndex[0]
        || value.length < paramIndex[1] ) {
      context.buildConstraintViolationWithTemplate("Method without the minimum 2 parameters for the cross parameter constraint" );
      return false;
    }
    if (!value[paramIndex[0]].getClass().equals(value[paramIndex[1]].getClass())) {
      context.buildConstraintViolationWithTemplate( "Dates aren't from the same type" );
      return false;
    }
    if (valueSupported(value[paramIndex[0]])) {
      context.buildConstraintViolationWithTemplate(String.format("Value %s not supported", value[paramIndex[0]]));
      return false;
    }
    if (valueSupported(value[paramIndex[1]])) {
      context.buildConstraintViolationWithTemplate(String.format("Value %s not supported", value[paramIndex[1]]));
      return false;
    }
    final Pair<Object, Object> fromAndTo = Pair.of(value[paramIndex[0]], value[paramIndex[1]]);
    final Object from = fromAndTo.getFirst();
    final Object to = fromAndTo.getSecond();
    final int expected = this.fromToType == FromAndToDate.FromToType.FROM_LOWER_THAN_OR_EQUAL_TO_TO ? 0 : 1;
    if (from instanceof Date || from instanceof Temporal) {
      return ((Comparable<Object>) to).compareTo(from) >= expected;
    }

    return true;
  }

  private static boolean valueSupported(final Object value) {
    return !(value instanceof Date) && !(value instanceof Temporal);
  }
}
