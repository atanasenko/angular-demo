package org.sleepless.demo.web.model;

public class AssignmentRequest {
    private long courseId;
    private long assignmentId;
    private String name;
    private int courseWeight;
    
    public long getCourseId() {
        return courseId;
    }
    
    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }
    
    public long getAssignmentId() {
        return assignmentId;
    }
    
    public void setAssignmentId(long assignmentId) {
        this.assignmentId = assignmentId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getCourseWeight() {
        return courseWeight;
    }
    
    public void setCourseWeight(int courseWeight) {
        this.courseWeight = courseWeight;
    }
}
