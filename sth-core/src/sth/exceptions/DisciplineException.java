package sth.exceptions;

abstract class DisciplineException extends Exception { 
  private String _name;
  DisciplineException(String name) { _name = name; }
  public String getName() { return _name; }
}
