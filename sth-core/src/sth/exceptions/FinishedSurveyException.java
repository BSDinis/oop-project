package sth.exceptions;

public class FinishedSurveyException extends SurveyException {
  public FinishedSurveyException(String disciplineName, String projectName) {
    super(disciplineName, projectName);
  }
}
