package sth.exceptions;

/** Exception thrown when the requested project already exists. */
public class ProjectAlreadyExistsException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201811140050L;

  /** Discipline name. */
  private String _discipline;
  /** Project name. */
  private String _project;

  /**
   * @param name
   */
  public ProjectAlreadyExistsException(String d, String p) {
    _discipline = d;
    _project = p;
  }

  /** @return discipline name */
  public String getDisciplineName() {
    return _discipline;
  }
  /** @return project name */
  public String getName() {
    return _project;
  }

}
