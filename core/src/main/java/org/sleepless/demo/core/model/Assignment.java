package org.sleepless.demo.core.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "assignment")
public class Assignment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;
    
    @Column
    private String name;
    
    @Column(name = "course_weight")
    private int courseWeight;
    
    @OneToMany(mappedBy = "assignment")
    private List<AssignmentGrade> assignmentGrades;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public Course getCourse() {
        return course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getCourseWeight() {
        return courseWeight;
    }
    
    public void setCourseWeight(int courseWeight) {
        this.courseWeight = courseWeight;
    }
    
    public List<AssignmentGrade> getAssignmentGrades() {
        return assignmentGrades;
    }
    
    public void setAssignmentGrades(List<AssignmentGrade> assignmentGrades) {
        this.assignmentGrades = assignmentGrades;
    }
}
