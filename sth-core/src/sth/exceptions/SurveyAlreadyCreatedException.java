package sth.exceptions;

public class SurveyAlreadyCreatedException extends SurveyException {
  public SurveyAlreadyCreatedException(String name) {
    super(name);
  }
}
