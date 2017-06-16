package ug.monografico32.model;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import static org.junit.Assert.*;

/**
 * Created by Jose Elias on 15/06/2017.
 */
public class PeriodoTest {

    Periodo p1;
    Periodo p2;


    public void periodoSetUp(){
        Calendar now = Calendar.getInstance();
        Date d1 = now.getTime();

        now.add(Calendar.MONTH, 1);
        Date d2 = now.getTime();

        p1 = new Periodo(d1, d2);
        p2 = new Periodo(d1, d2);
    }

    @Test
    public void testForEquality(){
        periodoSetUp();
        assertTrue(p1.equals(p2));
    }
}
