package sth;

public class SurveyOpenNotification
  extends SurveyNotification {
  SurveyOpenNotification(String d, String p) { super(d, p); }
  public String print(SurveyNotificationPrinter p) { return p.print(this); }
}
