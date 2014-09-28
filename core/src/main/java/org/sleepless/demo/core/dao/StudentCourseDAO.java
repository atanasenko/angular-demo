package org.sleepless.demo.core.dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.sleepless.demo.core.model.Course;
import org.sleepless.demo.core.model.StudentCourse;

@Named
public class StudentCourseDAO {
    
    @Inject
    private SessionFactory sessionFactory;
    
    @SuppressWarnings("unchecked")
    public List<StudentCourse> forCourse(Course course) {
        return (List<StudentCourse>) sessionFactory.getCurrentSession()
                .createCriteria(StudentCourse.class)
                .add(Restrictions.eq("course", course))
                .list();
    }
    
    public void save(StudentCourse course) {
        sessionFactory.getCurrentSession().save(course);
    }

    public void delete(StudentCourse course) {
        sessionFactory.getCurrentSession().delete(course);
    }
    
    public StudentCourse load(StudentCourse.Id id) {
        return (StudentCourse) sessionFactory.getCurrentSession().load(StudentCourse.class, id);
    }

}
