package sth.exceptions;

public class TooManyRepresentativesException extends Exception { 
  private String _courseName;
  public TooManyRepresentativesException(String name) { _courseName = name; }
  public getName() { return _courseName; }
}
