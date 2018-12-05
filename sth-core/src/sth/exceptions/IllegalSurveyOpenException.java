package sth.exceptions;

public class IllegalSurveyOpenException extends SurveyException {
  public IllegalSurveyOpenException(String disciplineName, String projectName) {
    super(disciplineName, projectName);
  }
}
