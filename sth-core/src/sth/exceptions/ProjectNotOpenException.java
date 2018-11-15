package sth.exceptions;

/** Exception thrown when the project is closed. */
public class ProjectNotOpenException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201811151121L;

  private String _name;

  /**
   * @param name
   */
  public ProjectNotOpenException(String name) {
    _name = name;
  }

  /** @return name */
  public int getName() {
    return _name;
  }

}

