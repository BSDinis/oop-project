package sth;

import java.util.Collection;

public abstract class SurveyNotificationPrinter {
  abstract public String print(SurveyOpenNotification n);
  abstract public String print(SurveyFinishNotification n);
  public String print(Collection<SurveyNotification> notifs) {
    if (notifs.size() == 0) return "";
    String res = ""; 
    int i;
    for (SurveyNotification notif : notifs)                             
      res += notif.print(this);

    return res.substring(0, res.length() - 1); 
  }
}
