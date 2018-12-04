package sth;

public class SurveyStudentPrinter 
  extends SurveyBasicPrinter
  implements SurveyPrinter {
  public String print(Survey.Finished s) {
    String res = defaultFormat(s.disciplineName(), s.projectName()) + '\n';
    res += " * Número de respostas: " + s.responsesNumber() + "\n";
    res += " * Tempos de resolução (horas) (médio): " + s.medHours();
    return res;
  }
}
