package sth.exceptions;

public class IllegalSurveyCloseException extends SurveyException {
  public IllegalSurveyCloseException(String disciplineName, String projectName) {
    super(disciplineName, projectName);
  }
}
