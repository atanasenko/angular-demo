package org.sleepless.demo.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "grades")
public class Grades {
    
    private List<Grade> grades;
    
    @XmlElement(name = "grade")
    public List<Grade> getGrades() {
        return grades;
    }
    
    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
    
    public static class Grade {
        private String name;
        
        private int value;
        
        @XmlAttribute
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        @XmlValue
        public int getValue() {
            return value;
        }
        
        public void setValue(int value) {
            this.value = value;
        }
    }
    
    public static List<Grade> parse(InputStream in) throws IOException {
        try {
            Unmarshaller um = JAXBContext.newInstance(Grades.class).createUnmarshaller();
            return ((Grades)um.unmarshal(in)).getGrades();
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }
}
