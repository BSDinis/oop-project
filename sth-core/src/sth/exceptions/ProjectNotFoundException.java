package sth.exceptions;

public class ProjectNotFoundException extends ProjectException {
  public ProjectNotFoundException(String d, String p) {
    super(d, p);
  }
}
