package sth.app.teaching;

import java.util.Collection;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.Survey;

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
    _form.parse();
    try {
      Collection<Survey> surveys = _receiver.getProjectSurveys(_disciplineName.value(), _projectName.value()); 
      for (Survey s : survey) {
        _display.addline(s); // FIXME
      }
    }
    catch (UnsuportedOperationException e) {
      _display.popup("Operação não suportada");
    }
    // FIXME : other exceptions
  }

}
