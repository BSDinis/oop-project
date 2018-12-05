package sth.exceptions;

public class NoSuchPersonNameException extends Exception {
  private String _name;
  public NoSuchPersonNameException(String name) {
    _name = name;
  }
  public String getName() {
    return _name;
  }

}
