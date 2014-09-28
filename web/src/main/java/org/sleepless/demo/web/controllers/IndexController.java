package org.sleepless.demo.web.controllers;

import javax.inject.Inject;

import org.sleepless.demo.core.dao.StudentDAO;
import org.sleepless.demo.core.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    
    @Inject
    private StudentDAO studentDao;
    
    @Transactional(readOnly = true)
    @RequestMapping("/")
    public String get() {
        for(Student student: studentDao.getStudents()) {
            System.out.println(student.getId() + ": " + student.getName());
        }
        return "static/index.html";
    }
    
}
