package ug.monografico32.model.validation.constraints.validators;

import ug.monografico32.model.Curso;
import ug.monografico32.model.validation.constraints.annotations.FechaInicioFinalValida;

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
      //System.out.println(c.getGrado()+"\n"+c.getNivel()+"\n"+c.getSeccion()+c.getFechaInicio()+"\n"+c.getFechaFinal());
      if( (c.getFechaFinal() == null) || (c.getFechaInicio() == null))
         return false;

      return c.getFechaFinal().after( c.getFechaInicio() );
   }
}
