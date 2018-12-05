package sth.app.printers;

import sth.Survey;
import sth.SurveyPrinter;
public abstract class SurveyBasicPrinter
  implements SurveyPrinter {
  public String print(Survey.Open s) {
    return defaultFormat(s.disciplineName(), s.projectName(), "(aberto)");
  }
  public String print(Survey.Created s) {                           
    return defaultFormat(s.disciplineName(), s.projectName(), "(por abrir)");
  }
  public String print(Survey.Closed s) {
    return defaultFormat(s.disciplineName(), s.projectName(), "(fechado)");
  }
  abstract public String print(Survey.Finished s);
  protected String defaultFormat(String discipline, String project, String label) {
    return discipline + " - " + project + " " + label;
  }
  protected String defaultFormat(String discipline, String project) {
    return discipline + " - " + project;
  }
}

