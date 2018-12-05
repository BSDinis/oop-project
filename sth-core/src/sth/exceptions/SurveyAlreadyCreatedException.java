package sth.exceptions;

public class SurveyAlreadyCreatedException extends SurveyException {
  public SurveyAlreadyCreatedException(String disciplineName, String projectName) {
    super(disciplineName, projectName);
  }
}
