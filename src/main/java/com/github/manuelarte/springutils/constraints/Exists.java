package com.github.manuelarte.springutils.constraints;


import com.github.manuelarte.springutils.validators.ExistsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistsValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Exists {

  /**
   * The entity/document class to check whether it exists. There should be a @Repository bean linked to the @Entity/@Document.
   * @return the entity that is going to be checked in the repository.
   */
  Class<?> value();

  /**
   * The constraint validation message to be shown in case the constraint fails
   * @return the error message to be shown
   */
  String message() default "Entity not found";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
