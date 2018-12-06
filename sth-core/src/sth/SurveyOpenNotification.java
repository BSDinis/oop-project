package sth;

public class SurveyOpenNotification
  extends SurveyNotification {
  SurveyOpenNotification(Survey s) { super(s); }
  public String print(SurveyNotificationPrinter p) { return p.print(this); }
}
