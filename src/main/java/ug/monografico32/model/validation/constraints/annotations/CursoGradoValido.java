/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.model.validation.constraints.annotations;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import ug.monografico32.model.validation.constraints.validators.CursoGradoValidator;

@Target({METHOD, TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint( validatedBy = CursoGradoValidator.class)
public @interface CursoGradoValido {
    
    String message() default "{CursoGradoValido.message}";
    
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
    
}
