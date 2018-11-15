package sth.exceptions;

/** Exception thrown when the requested no person is registered under a name. */
public class NoSuchPersonNameException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201811151301L;

  /** persons name. */
  private String _name;

  /**
   * @param name
   */
  public NoSuchPersonNameException(String name) {
    _name = name;
  }

  /** @return name */
  public String getName() {
    return _name;
  }

}
