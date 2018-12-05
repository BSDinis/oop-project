package sth.exceptions;

public class ProjectAlreadyExistsException extends ProjectException {
  public ProjectAlreadyExistsException(String d, String p) {
    super(d, p);
  }
}
