package sth.exceptions;

public class SurveyNotFoundException extends SurveyException {
  public SurveyNotFoundException(String disciplineName, String projectName) {
    super(disciplineName, projectName);
  }
}
