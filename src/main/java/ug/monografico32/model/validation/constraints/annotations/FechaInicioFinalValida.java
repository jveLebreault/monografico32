package ug.monografico32.model.validation.constraints.annotations;

import ug.monografico32.model.validation.constraints.validators.PeriodoStartAndFinishValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

@Target({METHOD, TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint( validatedBy = { PeriodoStartAndFinishValidator.class })
public @interface FechaInicioFinalValida {

    String message() default "{FechaInicioFinalValida.message}";

    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};

}
