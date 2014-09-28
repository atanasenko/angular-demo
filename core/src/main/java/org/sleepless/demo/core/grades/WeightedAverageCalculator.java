package org.sleepless.demo.core.grades;

import java.util.Collection;

import javax.inject.Named;

import org.sleepless.demo.core.model.AssignmentGrade;

@Named("weightedAverage")
public class WeightedAverageCalculator implements ICourseGradeCalculator {
    
    public static final WeightedAverageCalculator INSTANCE = new WeightedAverageCalculator();
    
    private WeightedAverageCalculator() { }

    @Override
    public int calculateCourseGrade(Collection<AssignmentGrade> grades) {
        
        int gradeSum = 0;
        int weightSum = 0;
        
        for(AssignmentGrade grade: grades) {
            int weight = grade.getAssignment().getCourseWeight();
            gradeSum += grade.getGrade() * weight;
            weightSum += weight;
        }
        
        double result = ((double) gradeSum) / weightSum;
        return (int) Math.round(result);
    }
}
