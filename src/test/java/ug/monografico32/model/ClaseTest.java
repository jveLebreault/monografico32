/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.model;

import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import ug.monografico32.model.Clase.CollisionException;
import static org.junit.Assert.*;

/**
 *
 * @author Administrador
 */
//Might be better to re write this class as SesionTest and check is  
//Sesion.checkForCollisions works ok and then make a different test class 
//to work with actual tests for Clase
public class ClaseTest {
    
    private Asignatura a;
    private Docente d;
    
    private Calendar now; 
    private Calendar later;
    
    @Before
    public void setUp(){
        a = new Asignatura("Biologia","014");
        d =  new Docente("Juan", "Valcez");
        now = Calendar.getInstance();
        later = Calendar.getInstance();
    }
    
    @Test(expected = CollisionException.class)
    public void sameStartAndFinishTime() throws CollisionException{
        
        Clase c = new Clase(a,d);
        
        later.add(Calendar.HOUR, 2);
        
        Sesion s1 = new Sesion(DayOfWeek.FRIDAY, now.getTime(), later.getTime());
        Sesion s2 = new Sesion(DayOfWeek.FRIDAY, now.getTime(), later.getTime());
        
            c.agregarSesion(s1);
            c.agregarSesion(s2);
        
    }
    
    @Test(expected = CollisionException.class)
    public void sesionContainsAnother() throws CollisionException{
        Clase c = new Clase(a,d);
        
        later.add(Calendar.HOUR,4);
        Sesion s1 = new Sesion(DayOfWeek.FRIDAY, now.getTime(), later.getTime());
        now.add(Calendar.HOUR, 1);
        later.add(Calendar.HOUR,-2);
        Sesion s2 = new Sesion(DayOfWeek.FRIDAY, now.getTime(), later.getTime());
        
        c.agregarSesion(s1);
        c.agregarSesion(s2);
    }
    
    @Test(expected = CollisionException.class)
    public void sesionColidesPartially() throws CollisionException{
        Clase c = new Clase(a,d);
        
        later.add(Calendar.HOUR, 2);
        Sesion s1 = new Sesion(DayOfWeek.FRIDAY, now.getTime(), later.getTime());
        
        now.add(Calendar.HOUR, 1);
        later.add(Calendar.HOUR, 1);
        Sesion s2 = new Sesion(DayOfWeek.FRIDAY, now.getTime(), later.getTime());
        
        c.agregarSesion(s1);
        c.agregarSesion(s2);
    }
    
    @Test
    public void agregarDosSesiones() throws CollisionException{
        Clase c = new Clase(a,d);
        
        later.add(Calendar.HOUR, 2);
        Sesion s1 = new Sesion(DayOfWeek.FRIDAY, now.getTime(), later.getTime());
        
        now.add(Calendar.HOUR,3);
        later.add(Calendar.HOUR, 5);
        
        Sesion s2 = new Sesion(DayOfWeek.FRIDAY, now.getTime(), later.getTime());
        c.agregarSesion(s1);
        c.agregarSesion(s2);
        assertTrue(c.getSesiones().size() == 2);
    }
}
