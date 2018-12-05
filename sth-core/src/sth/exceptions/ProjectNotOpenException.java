package sth.exceptions;

public class ProjectNotOpenException extends ProjectException{
  public ProjectNotOpenException(String d, String p) {
    super(d, p);
  }
}

