package sth.exceptions;

public class ImportFileException extends Exception {
  public ImportFileException() {
    // do nothing
  }
  public ImportFileException(String description) {
    super(description);
  }
  public ImportFileException(Exception cause) {
    super(cause);
  }
}
