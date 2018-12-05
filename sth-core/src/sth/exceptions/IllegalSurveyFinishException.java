package sth.exceptions;

public class IllegalSurveyFinishException extends SurveyException {
  public IllegalSurveyFinishException(String disciplineName, String projectName) {
    super(disciplineName, projectName);
  }
}
