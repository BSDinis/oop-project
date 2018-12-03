package sth.exceptions;

public class SurveyException extends Exception {
  private String _name;
  public SurveyException(String name) {
    _name = name;
  }
  public String getName() {
    return _name;
  }
}
