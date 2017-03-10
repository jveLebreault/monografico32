package ug.monografico32.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.util.Calendar;
import static org.junit.Assert.*;

/**
 * Created by Jose Elias on 09/03/2017.
 */
public class HorarioTest {

    private Horario horario = new Horario();

    private Docente d1 = new Docente("Juan", "Perez", "12345678910");
    private Docente d2 = new Docente("Alberto", "Mancebo", "98765432101");

    private Asignatura a1 = new Asignatura("Biologia 0104","BIO014");
    private Asignatura a2 = new Asignatura("Sociologia", "SOC014");
    private Asignatura a3 = new Asignatura("Informatica", "INF206");

    private Clase bio = new Clase(a1, d1);
    private Clase soc = new Clase(a2, d2);
    private Clase col = new Clase(a3, d1);

    private Sesion s1, s2, s3, s4;

    private Calendar start;
    private Calendar end;


    @Before
    public void setUpTest(){
        //Set up times
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        end.add(Calendar.HOUR, 2);

        //Set up sesions
        s1 = new Sesion(DayOfWeek.FRIDAY, start.getTime(), end.getTime());
        s2 = new Sesion(DayOfWeek.MONDAY, start.getTime(), end.getTime());

        start.add(Calendar.HOUR, 3);
        end.add(Calendar.HOUR, 3);
        s3 = new Sesion(DayOfWeek.MONDAY, start.getTime(), end.getTime());

        start.add(Calendar.HOUR, 3);
        end.add(Calendar.HOUR, 3);
        s4 = new Sesion(DayOfWeek.WEDNESDAY, start.getTime(), end.getTime());

        //Set up clases
        bio.agregarSesion(s1);
        bio.agregarSesion(s2);

        col.agregarSesion(s1);
        col.agregarSesion(s2);

        soc.agregarSesion(s3);
        soc.agregarSesion(s4);
    }

    @Test
    public void twoSesiones(){

        assertTrue( horario.agregarClase(bio) );
        assertTrue( horario.getAllSesions().size() == 2);
        assertTrue( horario.getSesionsByDayOfWeek().containsKey(DayOfWeek.FRIDAY));
        assertTrue( horario.getSesionsByDayOfWeek().containsKey(DayOfWeek.MONDAY));
    }

    @Test
    public void twoColisiones(){

        assertTrue( horario.agregarClase(bio) );
        assertFalse( horario.agregarClase(col) );
        assertTrue( horario.getColisiones().size() == 2 );
        assertTrue( horario.getColisiones().contains(s1));
    }

    @Test
    public void addAfterColision(){
        twoColisiones();
        assertTrue( horario.agregarClase(soc) );
        assertTrue( horario.getColisiones().isEmpty() );
        assertTrue( horario.getSesionsByDayOfWeek().containsKey(DayOfWeek.WEDNESDAY));
        assertTrue( horario.getSesionsByDayOfWeek().get(DayOfWeek.MONDAY).size() == 2);
    }

    @Test
    public void addToExistingClase(){
        Sesion s5 = new Sesion(DayOfWeek.SATURDAY, start.getTime(), end.getTime());
        bio.agregarSesion(s5);

        assertTrue( horario.agregarClase(bio) );

        int index = horario.getClases().indexOf(bio);
        Clase c1 = horario.getClases().get(index);

        assertTrue(c1.getSesiones().size() == 3);
        assertTrue( horario.getSesionsByDayOfWeek().containsKey(DayOfWeek.SATURDAY) );
        assertTrue( horario.getAllSesions().contains(s5));
    }

    @Test
    public void addToExistingAfterColision(){
        /*twoColisiones();
        System.out.println( horario.getColisiones().size());
        addToExistingClase();
        assertTrue( horario.getColisiones().isEmpty() );*/
    }
}
