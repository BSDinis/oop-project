package sth;

public class SurveyFinishNotification
  extends SurveyNotification {
  SurveyFinishNotification(Survey s) { super(s); }
  public String print(SurveyNotificationPrinter p) { return p.print(this); }
}
