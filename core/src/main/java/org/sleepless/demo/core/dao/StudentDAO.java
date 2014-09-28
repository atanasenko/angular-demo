package org.sleepless.demo.core.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.sleepless.demo.core.model.Student;

@Named
public class StudentDAO {
    
    @Inject
    private SessionFactory sessionFactory;
    
    @SuppressWarnings("unchecked")
    public List<Student> getStudents() {
        List<Student> fetched = (List<Student>) sessionFactory.getCurrentSession()
            .createCriteria(Student.class)
            .setFetchMode("courses", FetchMode.JOIN)
            .list();
        
        List<Student> result = new ArrayList<Student>();
        for(Student student: fetched) {
            if(!result.contains(student)) {
                result.add(student);
            }
        }
        return result;
    }
    
    public Student save(Student student) {
        sessionFactory.getCurrentSession().save(student);
        return student;
    }

    public void delete(Student student) {
        sessionFactory.getCurrentSession().delete(student);
    }
    
    public Student load(long id) {
        return (Student) sessionFactory.getCurrentSession().load(Student.class, id);
    }

}
