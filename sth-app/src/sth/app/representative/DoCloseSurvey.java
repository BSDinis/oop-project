package sth.app.representative;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

import sth.exceptions.DisciplineNotFoundException;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.exceptions.ProjectNotFoundException;
import sth.app.exceptions.NoSuchProjectException;
import sth.exceptions.SurveyNotFoundException;
import sth.app.exceptions.NoSurveyException;
import sth.exceptions.IllegalSurveyCloseException;
import sth.app.exceptions.ClosingSurveyException;


/**
 * 4.5.4. Close survey.
 */
public class DoCloseSurvey extends Command<SchoolManager> {

  private Input<String> _disciplineName;
  private Input<String> _projectName;

  /**
   * @param receiver
   */
  public DoCloseSurvey(SchoolManager receiver) {
    super(Label.CLOSE_SURVEY, receiver);
    _disciplineName = _form.addStringInput(Message.requestDisciplineName());
    _projectName = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.closeSurvey(_disciplineName.value(), _projectName.value()); 
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
    catch (IllegalSurveyCloseException e) {
      throw new ClosingSurveyException(e.getDisciplineName(), e.getProjectName());
    }
  }

}
