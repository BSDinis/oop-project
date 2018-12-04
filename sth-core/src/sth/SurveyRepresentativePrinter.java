package sth;

public class SurveyRepresentativePrinter
  extends SurveyBasicPrinter
  implements SurveyPrinter {
  public String print(Survey.Finished s) {
    String res = defaultFormat(s.disciplineName(), s.projectName());
    res += " - " + s.responsesNumber() + " respostas - " + s.medHours() + " horas";
    return res;
  }
}
