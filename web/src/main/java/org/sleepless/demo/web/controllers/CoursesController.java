package org.sleepless.demo.web.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.sleepless.demo.core.dao.AssignmentDAO;
import org.sleepless.demo.core.dao.AssignmentGradeDAO;
import org.sleepless.demo.core.dao.CourseDAO;
import org.sleepless.demo.core.dao.StudentCourseDAO;
import org.sleepless.demo.core.dao.StudentDAO;
import org.sleepless.demo.core.grades.ICourseGradeCalculator;
import org.sleepless.demo.core.model.Assignment;
import org.sleepless.demo.core.model.AssignmentGrade;
import org.sleepless.demo.core.model.Course;
import org.sleepless.demo.core.model.Student;
import org.sleepless.demo.core.model.StudentCourse;
import org.sleepless.demo.core.util.Grades;
import org.sleepless.demo.core.util.Grades.Grade;
import org.sleepless.demo.web.model.AssignmentRequest;
import org.sleepless.demo.web.model.StudentGrade;
import org.sleepless.demo.web.model.StudentGradeRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/courses")
public class CoursesController {
    
    @Inject
    private CourseDAO courses;

    @Inject
    private StudentDAO students;

    @Inject
    private StudentCourseDAO studentCourses;
    
    @Inject
    private AssignmentDAO assignments;
    
    @Inject
    private AssignmentGradeDAO assignmentGrades;
    
    @Inject
    private ICourseGradeCalculator  courseGradeCalculator;
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Transactional(readOnly = true)
    public List<Course> list() {
        return courses.getCourses();
    }
    
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Course add(@RequestBody Course course) {
        return courses.save(course);
    }
    
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    @Transactional(readOnly = true)
    public Course view(@PathVariable long id) {
        Course course = courses.load(id);
        course.getAssignments().size(); // force load
        return course;
    }
    
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public void update(@PathVariable long id, @RequestParam String name) {
        Course s = courses.load(id);
        s.setName(name);
        courses.save(s);
    }
    
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    public void delete(@PathVariable long id) {
        courses.delete(courses.load(id));
    }
    
    @RequestMapping(value = "{id}/assignments", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Assignment addAssignment(@PathVariable long id, @RequestBody AssignmentRequest ar) {
        Assignment a = new Assignment();
        a.setCourse(courses.load(id));
        a.setName(ar.getName());
        a.setCourseWeight(ar.getCourseWeight());
        
        return assignments.save(a);
    }
    
    @RequestMapping(value = "{courseId}/assignments/{assignmentId}", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public Assignment viewAssignment(@PathVariable long courseId, @PathVariable long assignmentId) {
        return assignments.get(assignmentId);
    }
    
    @RequestMapping(value = "{courseId}/assignments/{assignmentId}", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public void updateAssignment(@PathVariable long courseId, @PathVariable long assignmentId, @RequestBody AssignmentRequest ar) {
        Assignment a = assignments.load(assignmentId);
        a.setName(ar.getName());
        a.setCourseWeight(ar.getCourseWeight());
        assignments.save(a);
    }
    
    @RequestMapping(value = "{courseId}/assignments/{assignmentId}", method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    public void deleteAssignment(@PathVariable long courseId, @PathVariable long assignmentId) {
        Assignment a = assignments.load(assignmentId);
        assignments.delete(a);
    }
    
    @RequestMapping(value = "{courseId}/assignments/{assignmentId}/grades", method = RequestMethod.GET)
    @ResponseBody
    @Transactional(readOnly = true)
    public List<StudentGrade> getStudentGrades(@PathVariable long courseId, @PathVariable long assignmentId) {
        Course c = courses.load(courseId);
        Assignment a = assignments.load(assignmentId);
        
        return getStudentGrades(c, a);
    }
    
    private List<StudentGrade> getStudentGrades(Course course, Assignment assignment) {
        List<StudentGrade> grades = new ArrayList<StudentGrade>();
        List<StudentCourse> scs = studentCourses.forCourse(course);
        
        List<AssignmentGrade> assignmentGrades = assignment.getAssignmentGrades();
        for(StudentCourse sc: scs) {
            grades.add(new StudentGrade(sc.getStudent(), assignmentGrades));
        }
        return grades;
    }
    
    @RequestMapping(value = "{courseId}/assignments/{assignmentId}/grades/{studentId}", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public void updateGrade(@PathVariable long courseId, @PathVariable long assignmentId, @PathVariable int studentId, @RequestBody StudentGradeRequest gr) {
        Course c = courses.load(courseId);
        Assignment a = assignments.load(assignmentId);
        Student s = students.load(studentId);
        
        AssignmentGrade grade = assignmentGrades.get(new AssignmentGrade.Id(s, a));
        if(grade == null) {
            grade = new AssignmentGrade(s, a);
        }
        
        grade.setGrade(gr.getGrade());
        assignmentGrades.save(grade);
        
        // recalculate student course grade
        StudentCourse sc = studentCourses.load(new StudentCourse.Id(s, c));
        List<AssignmentGrade> studentCourseGrades = assignmentGrades.forStudentCourse(sc);
        sc.setCourseGrade(courseGradeCalculator.calculateCourseGrade(studentCourseGrades));
        studentCourses.save(sc);
    }
    
    @RequestMapping(value = "{courseId}/assignments/{assignmentId}/grades/upload", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public void uploadGrades(@PathVariable long courseId, @PathVariable long assignmentId, @RequestParam MultipartFile file) {
        Course c = courses.load(courseId);
        Assignment a = assignments.load(assignmentId);
        
        List<Grade> uploadedGrades;
        InputStream in = null;
        try {
            in = file.getInputStream();
            uploadedGrades = Grades.parse(in);
        } catch(IOException e) {
            // log it
            throw new IllegalStateException(e);
        } finally {
            if(in != null) try{ in.close(); } catch(IOException e){}
        }
        
        
        saveGrades(uploadedGrades, c, a);
    }

    private void saveGrades(List<Grade> uploadedGrades, Course course, Assignment assignment) {
        
        List<StudentCourse> students = studentCourses.forCourse(course);
        
        Map<String, StudentCourse> scmap = new HashMap<String, StudentCourse>();
        for(StudentCourse sc: students) {
            scmap.put(sc.getStudent().getName().toLowerCase(), sc);
        }
        
        for(Grade newGrade: uploadedGrades) {
            
            StudentCourse sc = scmap.get(newGrade.getName().toLowerCase());
            if(sc == null) continue;
            
            AssignmentGrade grade = assignmentGrades.get(new AssignmentGrade.Id(sc.getStudent(), assignment));
            if(grade == null) {
                grade = new AssignmentGrade(sc.getStudent(), assignment);
            }
            
            grade.setGrade(newGrade.getValue());
            assignmentGrades.save(grade);
            
            // recalculate student course grade
            List<AssignmentGrade> studentCourseGrades = assignmentGrades.forStudentCourse(sc);
            sc.setCourseGrade(courseGradeCalculator.calculateCourseGrade(studentCourseGrades));
            studentCourses.save(sc);
            
        }
    }
    
}
