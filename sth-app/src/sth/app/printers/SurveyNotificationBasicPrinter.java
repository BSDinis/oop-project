package sth.app.printers;

import sth.SurveyNotification;
import sth.SurveyNotificationPrinter;
import sth.SurveyOpenNotification;
import sth.SurveyFinishNotification;

public class SurveyNotificationBasicPrinter
  implements SurveyNotificationPrinter {
  public String print(SurveyOpenNotification n) { return "Pode preencher " + templateString(n); }
  public String print(SurveyFinishNotification n) { return "Resultados do " + templateString(n); }
  private String templateString(SurveyNotification n) {
    return "inquérito do projeto " + n.getProjectName() + " da disciplina " + n.getDisciplineName();
  }
}

