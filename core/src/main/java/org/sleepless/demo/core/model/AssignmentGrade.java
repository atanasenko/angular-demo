package org.sleepless.demo.core.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "assignment_grade")
@NamedQueries({
    @NamedQuery(
        name = "AssignmentGrade.forStudentCourse",
        query = "from AssignmentGrade where student = :student and assignment.course = :course"
    )
})
public class AssignmentGrade {
    
    @EmbeddedId
    private Id id;
    
    @ManyToOne
    @MapsId("studentId")
    private Student student;
    
    @ManyToOne
    @MapsId("assignmentId")
    private Assignment assignment;
    
    @Column
    private int grade;
    
    public AssignmentGrade() {
    }
    
    public AssignmentGrade(Student s, Assignment a) {
        setId(new AssignmentGrade.Id(s, a));
        setStudent(s);
        setAssignment(a);
    }

    public Id getId() {
        return id;
    }
    
    public void setId(Id id) {
        this.id = id;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public Assignment getAssignment() {
        return assignment;
    }
    
    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }
    
    public int getGrade() {
        return grade;
    }
    
    public void setGrade(int grade) {
        this.grade = grade;
    }
    
    @Embeddable
    public static class Id implements Serializable {
        
        private static final long serialVersionUID = 1L;

        @Column(name = "student_id")
        private long studentId;
        
        @Column(name = "assignment_id")
        private long assignmentId;
        
        public Id() {
        }
        
        public Id(Student student, Assignment assignment) {
            setStudentId(student.getId());
            setAssignmentId(assignment.getId());
        }
        
        public long getStudentId() {
            return studentId;
        }
        
        public void setStudentId(long studentId) {
            this.studentId = studentId;
        }
        
        public long getAssignmentId() {
            return assignmentId;
        }
        
        public void setAssignmentId(long assignmentId) {
            this.assignmentId = assignmentId;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(getStudentId(), getAssignmentId());
        }
        
        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Id) {
                Id that = (Id) obj;
                return getStudentId() == that.getStudentId() && getAssignmentId() == that.getAssignmentId();
            }
            return false;
        }
    }
    
}
