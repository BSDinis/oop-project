package sth.exceptions;


/**
 * Thrown when a professor is already teaching a discipline
 */

public class ProfessorAlreadyTeachingException extends Exception { 
  private int _id;
  private String _disciplineName;
  public ProfessorAlreadyTeachingException(int id, String disciplineName) {
    _id = id;
    _disciplineName = disciplineName;
  }
  public int getId() { return _id; }
  public String getDisciplineName() { return _disciplineName; }
}
