package org.sleepless.demo.core.dao;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;
import org.sleepless.demo.core.model.Assignment;

@Named
public class AssignmentDAO {
    
    @Inject
    private SessionFactory sessionFactory;
    
    public Assignment save(Assignment assignment) {
        sessionFactory.getCurrentSession().save(assignment);
        return assignment;
    }

    public void delete(Assignment assignment) {
        sessionFactory.getCurrentSession().delete(assignment);
    }
    
    public Assignment get(long id) {
        return (Assignment) sessionFactory.getCurrentSession().get(Assignment.class, id);
    }
    
    public Assignment load(long id) {
        return (Assignment) sessionFactory.getCurrentSession().load(Assignment.class, id);
    }
}
