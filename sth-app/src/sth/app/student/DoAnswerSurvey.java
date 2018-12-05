package sth.app.student;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import sth.SchoolManager;

import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.NoSurveyException;
import sth.exceptions.ProjectNotFoundException;
import sth.exceptions.DisciplineNotFoundException;
import sth.exceptions.SurveyNotFoundException;

/**
 * 4.4.2. Answer survey.
 */
public class DoAnswerSurvey extends Command<SchoolManager> {

  private Input<String> _disciplineName;
  private Input<String> _projectName;
  private Input<Integer> _hoursSpent;
  private Input<String> _comment;

  /**
   * @param receiver
   */
  public DoAnswerSurvey(SchoolManager receiver) {
    super(Label.ANSWER_SURVEY, receiver);
    _disciplineName = _form.addStringInput(Message.requestDisciplineName());
    _projectName = _form.addStringInput(Message.requestProjectName());
    _hoursSpent = _form.addIntegerInput(Message.requestProjectHours());
    _comment = _form.addStringInput(Message.requestComment());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.answerSurvey(
          _disciplineName.value(),
          _projectName.value(),
          _hoursSpent.value(),
          _comment.value()
          ); 
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
