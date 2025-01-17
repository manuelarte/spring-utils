package com.github.manuelarte.springutils.constraints;


import com.github.manuelarte.springutils.validators.ExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistsValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Exists {

  /**
   * The entity class to check if it exists. It needs to have a Repository interface also.
   * @return the entity that is going to be checked in the repository.
   */
  Class<?> value();

  String message() default "Entity not found";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
