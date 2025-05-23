package io.github.manuelarte.springutils.constraints.fromto;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(FromDate.List.class)
public @interface FromDate {

  /**
   * To distinguish possible groups when validating From and to dates
   * @return The from and to this annotation belongs to
   */
  String value() default "";

  @Target({ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @interface List {
    FromDate[] value();
  }

}
