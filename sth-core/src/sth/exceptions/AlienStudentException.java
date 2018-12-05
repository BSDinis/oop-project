package sth.exceptions;

import sth.Student;
public class AlienStudentException extends StudentException { 
  public AlienStudentException(Student s) { super(s);}
}
