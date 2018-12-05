package sth;

public class SurveyFinishNotification
  extends SurveyNotification {
  SurveyFinishNotification(String d, String p) { super(d, p); }
  public String print(SurveyNotificationPrinter p) { return p.print(this); }
}
