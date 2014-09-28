package org.sleepless.demo.core.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.sleepless.demo.core.util.Grades;
import org.sleepless.demo.core.util.Grades.Grade;

public class GradesParserTest {
    
    @Test
    public void testParse() throws Exception {
        List<Grade> grades;
        InputStream in = getClass().getResourceAsStream("grades.xml");
        try {
            
            grades = Grades.parse(in);
            
        } finally {
            try{ in.close(); } catch(IOException e) {}
        }
        
        assertNotNull(grades);
        assertEquals(2, grades.size());
        
        Grade g1 = grades.get(0);
        assertNotNull(g1);
        assertEquals("john", g1.getName());
        assertEquals(5, g1.getValue());
        
        Grade g2 = grades.get(1);
        assertNotNull(g2);
        assertEquals("tom", g2.getName());
        assertEquals(3, g2.getValue());
    }
}
