package sth;

public interface SurveyNotificationPrinter {
  public String print(SurveyOpenNotification n);
  public String print(SurveyFinishNotification n);
}
