package sth.app.student;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

/**
 * 4.4.1. Deliver project.
 */
public class DoDeliverProject extends Command<SchoolManager> {

  private Input<String> _disciplineName;
  private Input<String> _projectName;
  private Input<String> _delivery;

  /**
   * @param receiver
   */
  public DoDeliverProject(SchoolManager receiver) {
    super(Label.DELIVER_PROJECT, receiver);
    _disciplineName = _form.addStringInput(Message.requestDisciplineName());
    _projectName = _form.addStringInput(Message.requestProjectName());
    _delivery = _form.addIntegerInput(Message.requestDeliveryMessage());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.deliverProject(
          _disciplineName.value(),
          _projectName.value(),
          _delivery.value()
          ); 
    }
    catch (UnsuportedOperationException e) {
      _display.popup("Operação não suportada");
    }
    // FIXME : other exceptions
  }

}
