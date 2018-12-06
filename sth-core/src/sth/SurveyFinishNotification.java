package sth;

import java.io.Serializable;
public class SurveyFinishNotification
  extends SurveyNotification 
  implements Serializable {
  SurveyFinishNotification(Survey s) { super(s); }
  public String print(SurveyNotificationPrinter p) { return p.print(this); }
}
