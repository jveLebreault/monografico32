/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.model;

import java.time.temporal.ChronoUnit;
import java.time.Instant;
import java.time.LocalDate;
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
        return new Docente("Juan", "Perez");
    }
    
    public Instant getFechaInicio(){
        return Instant.now().plus(1, ChronoUnit.DAYS);
    }
    
    public Instant getFechaFinal(){
        return Instant.now().plus(3, ChronoUnit.DAYS);
    }
    
    @Test
    public void nivelInicialGradoConstraintTest() {
        
        Docente encargado = getDocenteEncargado();
        Instant inicio = getFechaInicio();
        Instant fin = getFechaFinal();
        
        Curso c = new Curso(Nivel.INICIAL, Grado.SEPTIMO, "B", encargado, 
                inicio, fin);
        //Nivel.BASICA.toString().toLowerCase();
        Set<ConstraintViolation<Curso>> violations =  validator.validate( c );
        for(ConstraintViolation<Curso> vv: violations){
            System.out.print(vv.getMessage());
        }
        assertEquals(1, violations.size());
        assertEquals("Nivel inicial no puede tener grado septimo", 
                violations.iterator().next().getMessage());
    }
    
    @Test
    public void nivelBasicoGradoConstraintTest(){
        Docente encargado = getDocenteEncargado();
        Instant inicio = getFechaInicio();
        Instant fin = getFechaFinal();
        
        Curso c = new Curso(Nivel.BASICA, Grado.KINDER, "B", encargado, 
                inicio, fin);
        
        Set<ConstraintViolation<Curso>> violations =  validator.validate( c );
        
        assertEquals(1, violations.size());
        assertEquals("Nivel basica no puede tener grado kinder", 
                violations.iterator().next().getMessage());
    }
    
    @Test
    public void nivelMedioGradoConstraintTest(){
        Docente encargado = getDocenteEncargado();
        Instant inicio = getFechaInicio();
        Instant fin = getFechaFinal();
        
        Curso c = new Curso(Nivel.MEDIA, Grado.PRE_PRIMARIO, "B", encargado, 
                inicio, fin);
        
        Set<ConstraintViolation<Curso>> violations =  validator.validate( c );
        
        assertEquals(1, violations.size());
        assertEquals("Nivel media no puede tener grado pre_primario", 
                violations.iterator().next().getMessage());
    }
    
}
