package sth.app.teaching;

import java.lang.UnsupportedOperationException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.Project;
import sth.exceptions.ProjectNotFoundException;
import sth.exceptions.DisciplineNotFoundException;
import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.NoSuchDisciplineException;

/**
 * 4.3.3. Show project submissions.
 */
public class DoShowProjectSubmissions extends Command<SchoolManager> {

  private Input<String> _disciplineName;
  private Input<String> _projectName;

  /**
   * @param receiver
   */
  public DoShowProjectSubmissions(SchoolManager receiver) {
    super(Label.SHOW_PROJECT_SUBMISSIONS, receiver);
    _disciplineName = _form.addStringInput(Message.requestDisciplineName());
    _projectName = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      Project p = _receiver.getProject(_disciplineName.value(), _projectName.value()); 
      _display.addLine(""+p);
    }
    catch (UnsupportedOperationException e) {
      _display.popup("Operação não suportada");
    }
    /* FIXME
    catch (DisciplineNotFoundException e) {
      new NoSuchDisciplineException(e.getName());
    }
    catch (ProjectNotFoundException e) {
      new NoSuchProjectException(e.getName(), _disciplineName.value());
    }
    */
  }

}
