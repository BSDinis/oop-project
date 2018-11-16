package sth.app.teaching;

import java.lang.UnsupportedOperationException;
import java.util.Map;

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
      Map<Student, String> submissions = _receiver.getProjectSubmissions(_disciplineName.value(), _projectName.value()); 
      for (Map.Entry<Student, String> submission : submissions.entrySet())
        _display.addLine("* " + submission.getKey().id() + " - " + submission.getValue());

      _display.display();
    }
    catch (UnsupportedOperationException e) {
      _display.popup("Operação não suportada");
    }
    catch (DisciplineNotFoundException e) {
      throw new NoSuchDisciplineException(e.getName());
    }
    catch (ProjectNotFoundException e) {
      throw new NoSuchProjectException(_disciplineName.value(), e.getName());
    }
  }

}
