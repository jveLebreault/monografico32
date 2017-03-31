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
import static org.junit.Assert.*;

/**
 *
 * @author Administrador
 */
public class SesionTest {
    
    private Calendar now; 
    private Calendar later;
    
    @Before
    public void setUp(){
        /*a = new Asignatura("Biologia","014");
        d =  new Docente("Juan", "Valcez", "123456789101");*/
        now = Calendar.getInstance();
        later = Calendar.getInstance();
    }
    
    @Test
    public void sameStartAndFinishTime(){

        later.add(Calendar.HOUR, 2);
        
        Sesion s1 = new Sesion(DayOfWeek.FRIDAY, now.getTime(), later.getTime());
        Sesion s2 = new Sesion(DayOfWeek.FRIDAY, now.getTime(), later.getTime());

        assertTrue( Sesion.checkForCollision(s1,s2) );
        
    }
    
    @Test
    public void sesionContainsAnother(){
        
        later.add(Calendar.HOUR,4);
        Sesion s1 = new Sesion(DayOfWeek.FRIDAY, now.getTime(), later.getTime());
        now.add(Calendar.HOUR, 1);
        later.add(Calendar.HOUR,-2);
        Sesion s2 = new Sesion(DayOfWeek.FRIDAY, now.getTime(), later.getTime());

        assertTrue( Sesion.checkForCollision(s1,s2) );
    }
    
    @Test
    public void sesionColidesPartially(){
        
        later.add(Calendar.HOUR, 2);
        Sesion s1 = new Sesion(DayOfWeek.FRIDAY, now.getTime(), later.getTime());
        
        now.add(Calendar.HOUR, 1);
        later.add(Calendar.HOUR, 1);
        Sesion s2 = new Sesion(DayOfWeek.FRIDAY, now.getTime(), later.getTime());

        assertTrue( Sesion.checkForCollision(s1,s2) );
        assertTrue( Sesion.checkForCollision(s2,s1) );
    }

}
