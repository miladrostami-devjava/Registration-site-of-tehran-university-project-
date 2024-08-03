package org.tehranuniversity.college.exceptions;

public class StudentNotFoundException extends Exception{
    public StudentNotFoundException() {
        super("Student not found!");
    }
}
