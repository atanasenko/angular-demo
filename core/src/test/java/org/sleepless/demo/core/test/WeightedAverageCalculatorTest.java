package org.sleepless.demo.core.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.sleepless.demo.core.grades.ICourseGradeCalculator;
import org.sleepless.demo.core.grades.WeightedAverageCalculator;
import org.sleepless.demo.core.model.Assignment;
import org.sleepless.demo.core.model.AssignmentGrade;

public class WeightedAverageCalculatorTest {
    
    @Test
    public void testWeightedAverage() {
        
        ICourseGradeCalculator calc = WeightedAverageCalculator.INSTANCE;
        
        List<AssignmentGrade> grades = new ArrayList<AssignmentGrade>();
        
        Assignment a1 = new Assignment();
        Assignment a2 = new Assignment();
        Assignment a3 = new Assignment();
        
        a1.setCourseWeight(1);
        a2.setCourseWeight(1);
        a3.setCourseWeight(6);
        
        AssignmentGrade ag;
        grades.add(ag = new AssignmentGrade());
        ag.setAssignment(a1);
        ag.setGrade(2);
        grades.add(ag = new AssignmentGrade());
        ag.setAssignment(a2);
        ag.setGrade(2);
        grades.add(ag = new AssignmentGrade());
        ag.setAssignment(a3);
        ag.setGrade(10);
        
        int awGrade = calc.calculateCourseGrade(grades);
        assertEquals(8, awGrade);
        
        a3.setCourseWeight(1);
        awGrade = calc.calculateCourseGrade(grades);
        assertEquals(5, awGrade);
        
    }
    
}
