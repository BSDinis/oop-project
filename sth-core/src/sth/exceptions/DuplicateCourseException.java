package sth.exceptions;
public class DuplicateCourseException extends Exception { 
  private String _name;
  public DuplicateCourseException(String name) { _name = name; }
  public String getName() { return _name; }
}
