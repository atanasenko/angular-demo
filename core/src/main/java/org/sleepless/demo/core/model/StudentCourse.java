package org.sleepless.demo.core.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "student_course")
public class StudentCourse {
    
    @EmbeddedId
    @JsonIgnore
    private Id id;
    
    @ManyToOne
    @MapsId("studentId")
    @JsonIgnore
    private Student student;
    
    @ManyToOne
    @MapsId("courseId")
    private Course course;
    
    @Column(name = "course_grade")
    private Integer courseGrade;
    
    public StudentCourse() {
    }
    
    public StudentCourse(Student s, Course c) {
        setId(new Id(s, c));
        setStudent(s);
        setCourse(c);
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
    
    public Course getCourse() {
        return course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
    public Integer getCourseGrade() {
        return courseGrade;
    }
    
    public void setCourseGrade(Integer courseGrade) {
        this.courseGrade = courseGrade;
    }
    
    @Override
    public int hashCode() {
        return getId().hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof StudentCourse) {
            StudentCourse that = (StudentCourse) obj;
            return getId().equals(that.getId());
        }
        return false;
    }
    
    @Embeddable
    public static class Id implements Serializable {
        
        private static final long serialVersionUID = 1L;

        @Column(name = "student_id")
        private long studentId;
        
        @Column(name = "course_id")
        private long courseId;
        
        public Id() {
        }
        public Id(Student student, Course course) {
            setStudentId(student.getId());
            setCourseId(course.getId());
        }
        
        public long getStudentId() {
            return studentId;
        }
        
        public void setStudentId(long studentId) {
            this.studentId = studentId;
        }
        
        public long getCourseId() {
            return courseId;
        }
        
        public void setCourseId(long courseId) {
            this.courseId = courseId;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(getStudentId(), getCourseId());
        }
        
        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Id) {
                Id that = (Id) obj;
                return getStudentId() == that.getStudentId() && getCourseId() == that.getCourseId();
            }
            return false;
        }
    }
}
