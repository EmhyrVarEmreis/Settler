package pl.morecraft.dev.settler.service.abstractService.annotation;

import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractServiceSingleFilter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BaseFilter {

    Class<? extends AbstractServiceSingleFilter> value();

}
