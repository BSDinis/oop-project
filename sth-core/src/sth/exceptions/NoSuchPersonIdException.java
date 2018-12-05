package sth.exceptions;

public class NoSuchPersonIdException extends Exception {
  private int _id;
  public NoSuchPersonIdException(int id) {
    _id = id;
  }
  public int getId() {
    return _id;
  }

}
