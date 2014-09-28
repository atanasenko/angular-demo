package org.sleepless.demo.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column
    private String name;
    
    @OneToMany(mappedBy = "student")
    private List<StudentCourse> courses;
    
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
    
    public List<StudentCourse> getCourses() {
        return courses;
    }
    
    public void setCourses(List<StudentCourse> courses) {
        this.courses = courses;
    }
    
    public void addCourse(StudentCourse course) {
        List<StudentCourse> courses = getCourses();
        if(courses == null) setCourses(courses = new ArrayList<StudentCourse>());
        courses.add(course);
    }
    
}
