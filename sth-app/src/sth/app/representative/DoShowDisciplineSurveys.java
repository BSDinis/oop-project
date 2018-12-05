package sth.app.representative;

import java.util.Collection;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.Survey;

import sth.exceptions.DisciplineNotFoundException;
import sth.app.exceptions.NoSuchDisciplineException;

import sth.SurveyPrinter;

/**
 * 4.5.6. Show discipline surveys.
 */
public class DoShowDisciplineSurveys extends Command<SchoolManager> {

  private Input<String> _disciplineName;

  /**
   * @param receiver
   */
  public DoShowDisciplineSurveys(SchoolManager receiver) {
    super(Label.SHOW_DISCIPLINE_SURVEYS, receiver);
    _disciplineName = _form.addStringInput(Message.requestDisciplineName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    class SurveyRepresentativePrinter implements SurveyPrinter {
      public String print(Survey.Open s) {
        return defaultFormat(s.disciplineName(), s.projectName(), "(aberto)");
      }
      public String print(Survey.Created s) {                                                                                
        return defaultFormat(s.disciplineName(), s.projectName(), "(por abrir)");
      }
      public String print(Survey.Closed s) {
        return defaultFormat(s.disciplineName(), s.projectName(), "(fechado)");
      }
      public String print(Survey.Finished s) {
        String res = defaultFormat(s.disciplineName(), s.projectName());
        res += " - " + s.responsesNumber() + " respostas - " + s.medHours() + " horas";
        return res;
      }

      private String defaultFormat(String discipline, String project, String label) {
        return discipline + " - " + project + " " + label;
      }
      private String defaultFormat(String discipline, String project) {
        return discipline + " - " + project;
      }
    }

    _form.parse();
    Collection<Survey> surveys; 
    try {
      surveys = _receiver.disciplineSurveys(_disciplineName.value()); 
      SurveyPrinter printer = new SurveyRepresentativePrinter();
      for (Survey s : surveys) 
        _display.addLine(s.print(printer)); 

      _display.display();
    }
    catch (DisciplineNotFoundException e) {
      throw new NoSuchDisciplineException(e.getName());
    }
  }

}
