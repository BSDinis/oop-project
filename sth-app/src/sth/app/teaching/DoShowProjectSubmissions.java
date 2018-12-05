package sth.app.teaching;

import java.util.Collection;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import sth.SchoolManager;
import sth.Student;
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
      // submissions come in `<id>|<submission>` format
      Collection<String> submissions = _receiver.projectSubmissions(_disciplineName.value(), _projectName.value()); 
      _display.addLine(_disciplineName.value() + " - " + _projectName.value());
      for (String submission : submissions)
        _display.addLine("* " + submission.replaceFirst("\\|", " - "));

      _display.display();
    }
    catch (DisciplineNotFoundException e) {
      throw new NoSuchDisciplineException(e.getName());
    }
    catch (ProjectNotFoundException e) {
      throw new NoSuchProjectException(e.getDisciplineName(), e.getName());
    }
  }

}
