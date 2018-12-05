package sth.exceptions;

public class SurveyNotEmptyException extends SurveyException {
  public SurveyNotEmptyException(String disciplineName, String projectName) {
    super(disciplineName, projectName);
  }
}
