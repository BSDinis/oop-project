package sth.app.representative;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.Survey;

import sth.exceptions.DisciplineNotFoundException;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.exceptions.ProjectNotFoundException;
import sth.app.exceptions.NoSuchProjectException;
import sth.exceptions.SurveyNotFoundException;
import sth.app.exceptions.NoSurveyException;
import sth.exceptions.SurveyNotEmptyException;
import sth.app.exceptions.NonEmptySurveyException;
import sth.exceptions.FinishedSurveyException;
import sth.app.exceptions.SurveyFinishedException;



/**
 * 4.5.2. Cancel survey.
 */
public class DoCancelSurvey extends Command<SchoolManager> {

  private Input<String> _disciplineName;
  private Input<String> _projectName;

  /**
   * @param receiver
   */
  public DoCancelSurvey(SchoolManager receiver) {
    super(Label.CANCEL_SURVEY, receiver);
    _disciplineName = _form.addStringInput(Message.requestDisciplineName());
    _projectName = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.cancelSurvey(_disciplineName.value(), _projectName.value()); 
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
    catch (SurveyNotEmptyException e) {
      throw new NonEmptySurveyException(_disciplineName.value(), e.getName());
    }
    catch (FinishedSurveyException e) {
      throw new SurveyFinishedException(_disciplineName.value(), e.getName());
    }
  }

}
