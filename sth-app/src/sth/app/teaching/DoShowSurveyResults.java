package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.Survey;

import sth.exceptions.ProjectNotFoundException;
import sth.exceptions.DisciplineNotFoundException;
import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.NoSurveyException;
import sth.exceptions.SurveyNotFoundException;
import sth.app.exceptions.NoSuchDisciplineException;

import sth.SurveyPrinter;
import sth.app.printers.SurveyBasicPrinter;


/**
 * 4.3.5. Show survey results.
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
    class SurveyProfessorPrinter 
        extends SurveyBasicPrinter
        implements SurveyPrinter {

      public String print(Survey.Finished s) {
        String res = defaultFormat(s.disciplineName(), s.projectName());
        res += "\n * Número de submissões: " + s.submissionNumber();
        res += "\n * Número de respostas: " + s.responsesNumber();
        res += "\n * Tempos de resolução (horas) (mínimo, médio, máximo): " + s.minHours() + ", " + s.avgHours() + ", " + s.maxHours();
        return res;
      }
    }
    _form.parse();
    try {
      Survey s = _receiver.getSurvey(_disciplineName.value(), _projectName.value()); 
      _display.addLine(s.print(new SurveyProfessorPrinter())); 
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
