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
        return new Docente("Juan", "Perez", "123456789101");
    }
    
    public Date getFechaInicio(){
        return Date.from( Instant.now().plus(1, ChronoUnit.DAYS) );
    }
    
    public Date getFechaFinal(){
        return Date.from(Instant.now().plus(3, ChronoUnit.DAYS));
    }
    
    @Test
    public void nivelInicialGradoConstraintTest() {
        
        Docente encargado = getDocenteEncargado();
        Date inicio = getFechaInicio();
        Date fin = getFechaFinal();
        
        Curso c = new Curso(Nivel.INICIAL, Grado.SEPTIMO, "B", encargado, 
                inicio, fin);
        //Nivel.BASICA.toString().toLowerCase();
        Set<ConstraintViolation<Curso>> violations =  validator.validate( c );

        assertEquals(1, violations.size());
        assertEquals("Nivel inicial no puede tener grado 7mo",
                violations.iterator().next().getMessage());
    }
    
    @Test
    public void nivelBasicoGradoConstraintTest(){
        Docente encargado = getDocenteEncargado();
        Date inicio = getFechaInicio();
        Date fin = getFechaFinal();
        
        Curso c = new Curso(Nivel.BASICA, Grado.KINDER, "B", encargado, 
                inicio, fin);
        
        Set<ConstraintViolation<Curso>> violations =  validator.validate( c );
        
        assertEquals(1, violations.size());
        assertEquals("Nivel basica no puede tener grado Kinder",
                violations.iterator().next().getMessage());
    }
    
    @Test
    public void nivelMedioGradoConstraintTest(){
        Docente encargado = getDocenteEncargado();
        Date inicio = getFechaInicio();
        Date fin = getFechaFinal();
        
        Curso c = new Curso(Nivel.MEDIA, Grado.PRE_PRIMARIO, "B", encargado, 
                inicio, fin);
        
        Set<ConstraintViolation<Curso>> violations =  validator.validate( c );
        
        assertEquals(1, violations.size());
        assertEquals("Nivel media no puede tener grado Pre Primario",
                violations.iterator().next().getMessage());
    }

    @Test
    public void fechaInicioFinalConstraintTest(){

        Docente encargado = getDocenteEncargado();
        Date inicio = getFechaInicio();
        Date fin = getFechaFinal();

        Curso c = new Curso(Nivel.INICIAL, Grado.KINDER, "A", encargado, fin, inicio);

        Set<ConstraintViolation<Curso>> violations = validator.validate( c );

        assertEquals(1, violations.size());
        assertEquals("Fecha de inicio deber ser anterior a fecha final",
                violations.iterator().next().getMessage());

    }
    
}
