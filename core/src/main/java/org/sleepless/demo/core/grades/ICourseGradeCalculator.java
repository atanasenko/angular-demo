package org.sleepless.demo.core.grades;

import java.util.Collection;

import org.sleepless.demo.core.model.AssignmentGrade;

public interface ICourseGradeCalculator {
    
    int calculateCourseGrade(Collection<AssignmentGrade> grades);
    
}
