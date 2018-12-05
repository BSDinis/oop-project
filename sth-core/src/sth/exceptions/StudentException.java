package sth.exceptions;

import sth.Student;

abstract class StudentException extends Exception { 
  private int _id;
  private String _name = "";
  StudentException(Student s) { _id = s.id(); _name = s.name(); }
  StudentException(int id) { _id = id; }
  public int getId() { return _id; }
  public String getName() { return _name; }
}
