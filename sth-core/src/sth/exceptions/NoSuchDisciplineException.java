package sth.exceptions;

/** Exception thrown when the requested dicipline does not exist. */
public class NoSuchDisciplineException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201811140050L;

  /** Discipline name. */
  private String _name;

  /**
   * @param name
   */
  public NoSuchDisciplineException(String name) {
    _name = name;
  }

  /** @return name */
  public String getName() {
    return _name;
  }

}
