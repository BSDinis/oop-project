package sth.exceptions;

public class DuplicatePersonException extends Exception { 
  private int _id;
  public DuplicatePersonException(int id) { _id = id; }
  public int getId() { return _id; }
}
