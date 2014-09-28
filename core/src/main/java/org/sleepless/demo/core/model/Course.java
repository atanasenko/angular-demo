package org.sleepless.demo.core.model;

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
@Table(name = "course")
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column
    private String name;
    
    @OneToMany(mappedBy = "course")
    private List<Assignment> assignments;
    
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
    
    public List<Assignment> getAssignments() {
        return assignments;
    }
    
    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }
    
    public void addAssignment(Assignment assignment) {
        List<Assignment> assignments = getAssignments();
        if(assignments == null) setAssignments(assignments = new ArrayList<Assignment>());
        assignments.add(assignment);
    }
}
