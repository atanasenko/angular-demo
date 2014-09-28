package org.sleepless.demo.web.controllers;

import java.util.List;

import javax.inject.Inject;

import org.sleepless.demo.core.dao.CourseDAO;
import org.sleepless.demo.core.dao.StudentCourseDAO;
import org.sleepless.demo.core.dao.StudentDAO;
import org.sleepless.demo.core.model.Course;
import org.sleepless.demo.core.model.Student;
import org.sleepless.demo.core.model.StudentCourse;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/students")
public class StudentsController {
    
    @Inject
    private StudentDAO students;
    
    @Inject
    private CourseDAO courses;
    
    @Inject
    private StudentCourseDAO studentCourses;
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Transactional(readOnly = true)
    public List<Student> list() {
        return students.getStudents();
    }
    
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Student add(@RequestBody Student student) {
        return students.save(student);
    }
    
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    @Transactional(readOnly = true)
    public Student view(@PathVariable long id) {
        Student s = students.load(id);
        s.getCourses().size(); // force load
        return s;
    }
    
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public void update(@PathVariable long id, @RequestParam String name) {
        Student s = students.load(id);
        s.setName(name);
        students.save(s);
    }
    
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    public void delete(@PathVariable long id) {
        students.delete(students.load(id));
    }
    
    @RequestMapping(value = "{id}/courses/{courseId}", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public void addCourse(@PathVariable long id, @PathVariable long courseId) {
        Student s = students.load(id);
        Course c = courses.load(courseId);
        studentCourses.save(new StudentCourse(s, c));
    }
    
    @RequestMapping(value = "{id}/courses/{courseId}", method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    public void removeCourse(@PathVariable long id, @PathVariable long courseId) {
        Student s = students.load(id);
        Course c = courses.load(courseId);
        studentCourses.delete(new StudentCourse(s, c));
    }
}
