package sth.exceptions;

abstract class ProjectException extends Exception { 
  private String _name;
  private String _disciplineName;
  ProjectException(String disciplineName, String name) { 
    _disciplineName = disciplineName; 
    _name = name; 
  }
  public String getName() { return _name; }
  public String getDisciplineName() { return _disciplineName; }
}
