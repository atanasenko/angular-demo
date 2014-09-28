package org.sleepless.demo.core.dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;
import org.sleepless.demo.core.model.AssignmentGrade;
import org.sleepless.demo.core.model.StudentCourse;

@Named
public class AssignmentGradeDAO {
    
    @Inject
    private SessionFactory sessionFactory;
    
    @SuppressWarnings("unchecked")
    public List<AssignmentGrade> forStudentCourse(StudentCourse studentCourse) {
        
        return (List<AssignmentGrade>) sessionFactory.getCurrentSession()
            .getNamedQuery("AssignmentGrade.forStudentCourse")
            .setParameter("student", studentCourse.getStudent())
            .setParameter("course", studentCourse.getCourse())
            .list();
        
    }
    
    public void save(AssignmentGrade grade) {
        sessionFactory.getCurrentSession().save(grade);
    }

    public void delete(AssignmentGrade grade) {
        sessionFactory.getCurrentSession().delete(grade);
    }
    
    public AssignmentGrade get(AssignmentGrade.Id id) {
        return (AssignmentGrade) sessionFactory.getCurrentSession().get(AssignmentGrade.class, id);
    }
}
