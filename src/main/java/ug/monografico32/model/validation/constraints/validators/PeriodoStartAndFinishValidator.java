package ug.monografico32.model.validation.constraints.validators;

import ug.monografico32.model.Periodo;
import ug.monografico32.model.validation.constraints.annotations.FechaInicioFinalValida;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PeriodoStartAndFinishValidator implements ConstraintValidator<FechaInicioFinalValida, Periodo> {

    @Override
    public void initialize(FechaInicioFinalValida constraint) {
    }

    @Override
    public boolean isValid(Periodo p, ConstraintValidatorContext context) {
        if( p == null){
            return true;
        }

        return p.getFechaFinal().after( p.getFechaInicio() );
    }
}
