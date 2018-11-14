package sth.app.representative;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

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
    _form.parse();
    Collection<Survey> surveys; 
    try {
      surveys = _receiver.getDisciplineSurveys(_disciplineName.value()); 
    }
    catch (UnsuportedOperationException e) {
      _display.popup("Operação não suportada");
    }


    for (Survey s : surveys) {
      _display.addline(s); // FIXME
    }
  }

}
