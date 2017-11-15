/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.model;

import java.time.temporal.ChronoUnit;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Administrador
 */
public class CursoTest {
    
    static private Validator validator;
    
    @Before
    public void testSetup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    
    public Docente getDocenteEncargado(){
        Docente docente = new Docente("Juan", "Perez", "123456789101");
        docente.setPassword("123456");
        docente.setUsername("docente1");
        docente.addAuthority(Roles.DOCENTE);
        return docente;
    }
    
    public Date getFechaInicio(){
        return Date.from( Instant.now().plus(1, ChronoUnit.DAYS) );
    }
    
    public Date getFechaFinal(){
        return Date.from(Instant.now().plus(3, ChronoUnit.DAYS));
    }

    public Periodo getPeriodo(){
        return new Periodo( getFechaInicio(), getFechaFinal());
    }
    
    @Test
    public void nivelInicialGradoConstraintTest() {
        
        Docente encargado = getDocenteEncargado();
        Periodo periodo = getPeriodo();
        
        Curso c = new Curso(Nivel.INICIAL, Grado.SEPTIMO, "B", encargado, 
                periodo);
        //Nivel.BASICA.toString().toLowerCase();
        Set<ConstraintViolation<Curso>> violations =  validator.validate( c );

        Iterator<ConstraintViolation<Curso>> iter = violations.iterator();
        System.out.println("\n\n\n");
        while (iter.hasNext()){
            ConstraintViolation<Curso> violation = iter.next();

            System.out.println(violation.getMessage()+"\n"+violation.getPropertyPath().toString());
        }
        System.out.println("\n\n\n");


        assertEquals(1, violations.size());
        assertEquals("Nivel inicial no puede tener grado 7mo",
                violations.iterator().next().getMessage());
    }
    
    @Test
    public void nivelBasicoGradoConstraintTest(){
        Docente encargado = getDocenteEncargado();
        Periodo periodo = getPeriodo();
        
        Curso c = new Curso(Nivel.BASICA, Grado.KINDER, "B", encargado, 
                periodo);
        
        Set<ConstraintViolation<Curso>> violations =  validator.validate( c );
        
        assertEquals(1, violations.size());
        assertEquals("Nivel basica no puede tener grado Kinder",
                violations.iterator().next().getMessage());
    }
    
    @Test
    public void nivelMedioGradoConstraintTest(){
        Docente encargado = getDocenteEncargado();
        Periodo periodo = getPeriodo();
        
        Curso c = new Curso(Nivel.MEDIA, Grado.PRE_PRIMARIO, "B", encargado, 
                periodo);
        
        Set<ConstraintViolation<Curso>> violations =  validator.validate( c );
        
        assertEquals(1, violations.size());
        assertEquals("Nivel media no puede tener grado Pre Primario",
                violations.iterator().next().getMessage());
    }
    
}
