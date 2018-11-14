package sth.app.student;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

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
    _comment = _form.addIntegerInput(Message.requestComment());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.answerSurvey(
          _disciplineName.value(),
          _projectName.value(),
          _hoursSpent.valuer(),
          _comment.value()
          ); 
    }
    catch (UnsuportedOperationException e) {
      _display.popup("Operação não suportada");
    }
    // FIXME : other exceptions

  }

}
