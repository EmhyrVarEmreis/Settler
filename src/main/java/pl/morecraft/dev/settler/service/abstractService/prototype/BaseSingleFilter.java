package pl.morecraft.dev.settler.service.abstractService.prototype;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BaseSingleFilter {

    String name() default "unnamed";

    SingleFilterApplicableTypes[] types();

}
