package org.sleepless.demo.core.dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;
import org.sleepless.demo.core.model.Course;

@Named
public class CourseDAO {
    
    @Inject
    private SessionFactory sessionFactory;
    
    @SuppressWarnings("unchecked")
    public List<Course> getCourses() {
        return (List<Course>) sessionFactory.getCurrentSession()
            .createCriteria(Course.class)
            .list();
    }
    
    public Course save(Course course) {
        sessionFactory.getCurrentSession().save(course);
        return course;
    }

    public void delete(Course course) {
        sessionFactory.getCurrentSession().delete(course);
    }
    
    public Course load(long id) {
        return (Course) sessionFactory.getCurrentSession().load(Course.class, id);
    }

}
