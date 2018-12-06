package sth;

import java.io.Serializable;

public class SurveyOpenNotification
  extends SurveyNotification 
  implements Serializable {
  SurveyOpenNotification(Survey s) { super(s); }
  public String print(SurveyNotificationPrinter p) { return p.print(this); }
}
