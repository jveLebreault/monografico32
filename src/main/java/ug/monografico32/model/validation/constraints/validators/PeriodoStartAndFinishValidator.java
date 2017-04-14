package ug.monografico32.model.validation.constraints.validators;

import ug.monografico32.model.Periodo;
import ug.monografico32.model.validation.constraints.annotations.FechaInicioFinalValida;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Jose Elias on 13/04/2017.
 */
public class PeriodoStartAndFinishValidator implements ConstraintValidator<FechaInicioFinalValida, Periodo> {

    @Override
    public void initialize(FechaInicioFinalValida constraint) {
    }

    @Override
    public boolean isValid(Periodo p, ConstraintValidatorContext context) {
        if( p == null){
            return true;
        }
        //System.out.println(c.getGrado()+"\n"+c.getNivel()+"\n"+c.getSeccion()+c.getFechaInicio()+"\n"+c.getFechaFinal());
        if( (p.getFechaFinal() == null) || (p.getFechaInicio() == null))
            return false;

        return p.getFechaFinal().after( p.getFechaInicio() );
    }
}
