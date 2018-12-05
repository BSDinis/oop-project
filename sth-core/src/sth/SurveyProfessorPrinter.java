package sth;

// FIXME I don't belong
public class SurveyProfessorPrinter 
  extends SurveyBasicPrinter
  implements SurveyPrinter {

  public String print(Survey.Finished s) {
    String res = defaultFormat(s.disciplineName(), s.projectName()) + '\n';
    res += " * Número de submissões: " + s.submissionNumber() + "\n";
    res += " * Número de respostas: " + s.responsesNumber() + "\n";
    res += " * Tempos de resolução (horas) (mínimo, médio, máximo): " + s.minHours() + ", " + s.medHours() + ", " + s.maxHours(); 
    return res;
  }
}
