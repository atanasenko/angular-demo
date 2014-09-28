package org.sleepless.demo.web.model;

import java.util.List;

import org.sleepless.demo.core.model.AssignmentGrade;
import org.sleepless.demo.core.model.Student;

public class StudentGrade {
    
    private long id;
    private String name;
    private Integer grade;
    
    public StudentGrade() {
    }
    
    public StudentGrade(Student student, List<AssignmentGrade> grades) {
        setId(student.getId());
        setName(student.getName());
        for(AssignmentGrade grade: grades) {
            if(grade.getStudent().getId() == student.getId()) {
                setGrade(grade.getGrade());
                break;
            }
        }
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getGrade() {
        return grade;
    }
    
    public void setGrade(Integer grade) {
        this.grade = grade;
    }
    
}
