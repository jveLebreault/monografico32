/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.model.validation.constraints.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import ug.monografico32.model.Curso;
import ug.monografico32.model.Grado;
import ug.monografico32.model.validation.constraints.annotations.CursoGradoValido;

/**
 *
 * @author Administrador
 */
public class CursoGradoValidator implements ConstraintValidator<CursoGradoValido, Curso>{

    @Override
    public void initialize(CursoGradoValido a) {
        //NOOP
    }

    @Override
    public boolean isValid(Curso c, ConstraintValidatorContext cvc) {
        if(c == null)
            return true;
        
        Grado gradoActual = c.getGrado();

        switch( c.getNivel() ){
            case INICIAL:
                return ( gradoActual == Grado.KINDER || 
                        gradoActual == Grado.PRE_PRIMARIO );
                
            case BASICA:
                return(gradoActual != Grado.KINDER && 
                   gradoActual != Grado.PRE_PRIMARIO);
                
            case MEDIA:
                return( gradoActual.getOrdinal()<4 && (gradoActual != Grado.KINDER &&
                    gradoActual != Grado.PRE_PRIMARIO) );
                    
            default:
                return false;
        }
    }
    
}
