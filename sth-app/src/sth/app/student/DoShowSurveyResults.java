package sth.app.student;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.Survey;

import sth.SurveyPrinter;
import sth.app.printers.SurveyBasicPrinter;

import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.NoSurveyException;
import sth.exceptions.ProjectNotFoundException;
import sth.exceptions.SurveyNotFoundException;
import sth.exceptions.DisciplineNotFoundException;



/**
 * 4.4.3. Show survey results.
 */
public class DoShowSurveyResults extends Command<SchoolManager> {

  private Input<String> _disciplineName;
  private Input<String> _projectName;

  /**
   * @param receiver
   */
  public DoShowSurveyResults(SchoolManager receiver) {
    super(Label.SHOW_SURVEY_RESULTS, receiver);
    _disciplineName = _form.addStringInput(Message.requestDisciplineName());
    _projectName = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    class SurveyStudentPrinter 
      extends SurveyBasicPrinter
      implements SurveyPrinter {
      public String print(Survey.Finished s) {
        String res = defaultFormat(s.disciplineName(), s.projectName()) + '\n';
        res += " * Número de respostas: " + s.responsesNumber() + "\n";
        res += " * Tempos de resolução (horas) (médio): " + s.medHours();
        return res;
      }
    }

    _form.parse();
    try {
      Survey s = _receiver.studentGetSurvey( _disciplineName.value(), _projectName.value()); 
      SurveyPrinter printer = new SurveyStudentPrinter();
      _display.addLine(s.print(printer)); 
      _display.display();
    }
    catch (DisciplineNotFoundException e) {
      throw new NoSuchDisciplineException(e.getName());
    }
    catch (ProjectNotFoundException e) {
      throw new NoSuchProjectException(e.getDisciplineName(), e.getName());
    }
    catch (SurveyNotFoundException e) {
      throw new NoSurveyException(e.getDisciplineName(), e.getProjectName());
    }
  }

}
