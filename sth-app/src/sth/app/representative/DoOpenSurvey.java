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
import sth.exceptions.IllegalSurveyOpenException;
import sth.app.exceptions.OpeningSurveyException;

/**
 * 4.5.3. Open survey.
 */
public class DoOpenSurvey extends Command<SchoolManager> {

  private Input<String> _disciplineName;
  private Input<String> _projectName;

  /**
   * @param receiver
   */
  public DoOpenSurvey(SchoolManager receiver) {
    super(Label.OPEN_SURVEY, receiver);
    _disciplineName = _form.addStringInput(Message.requestDisciplineName());
    _projectName = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.openSurvey(_disciplineName.value(), _projectName.value()); 
    }
    catch (DisciplineNotFoundException e) {
      throw new NoSuchDisciplineException(e.getName());
    }
    catch (ProjectNotFoundException e) {
      throw new NoSuchProjectException(_disciplineName.value(), e.getName());
    }
    catch (SurveyNotFoundException e) {
      throw new NoSurveyException(_disciplineName.value(), e.getName());
    }
    catch (IllegalSurveyOpenException e) {
      throw new OpeningSurveyException(_disciplineName.value(), e.getName());
    }
  }

}
