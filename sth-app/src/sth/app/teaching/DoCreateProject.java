package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.exceptions.DisciplineNotFoundException;

import sth.app.exceptions.DuplicateProjectException;
import sth.exceptions.ProjectAlreadyExistsException;

/**
 * 4.3.1. Create project.
 */
public class DoCreateProject extends Command<SchoolManager> {

  private Input<String> _disciplineName;
  private Input<String> _projectName;

  /**
   * @param receiver
   */
  public DoCreateProject(SchoolManager receiver) {
    super(Label.CREATE_PROJECT, receiver);
    _disciplineName = _form.addStringInput(Message.requestDisciplineName());
    _projectName = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.createProject(_disciplineName.value(), _projectName.value()); 
    }
    catch (DisciplineNotFoundException e) {
      throw new NoSuchDisciplineException(e.getName());
    }
    catch (ProjectAlreadyExistsException e) {
      throw new DuplicateProjectException(e.getDisciplineName(), e.getName());
    }
  }

}
