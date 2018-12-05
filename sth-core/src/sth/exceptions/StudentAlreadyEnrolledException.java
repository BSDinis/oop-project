package sth.exceptions;

import sth.Student;
public class StudentAlreadyEnrolledException extends StudentException { 
  public StudentAlreadyEnrolledException(Student s) { super(s); }
}
