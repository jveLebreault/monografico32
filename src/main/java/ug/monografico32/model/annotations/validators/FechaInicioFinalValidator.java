package ug.monografico32.model.annotations.validators;

import ug.monografico32.model.Curso;
import ug.monografico32.model.annotations.CursoGradoValido;
import ug.monografico32.model.annotations.FechaInicioFinalValida;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Jose Elias on 28/11/2016.
 */
public class FechaInicioFinalValidator implements ConstraintValidator<FechaInicioFinalValida, Curso> {

   @Override
   public void initialize(FechaInicioFinalValida constraint) {
   }

   @Override
   public boolean isValid(Curso c, ConstraintValidatorContext context) {
      if( c == null){
         return true;
      }

      return c.getFechaFinal().isAfter( c.getFechaInicio() );
   }
}
