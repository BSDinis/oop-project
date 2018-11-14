package sth.app.person;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

/**
 * 4.2.2. Change phone number.
 */
public class DoChangePhoneNumber extends Command<SchoolManager> {

  /**
   * @param receiver
   */
  public DoChangePhoneNumber(SchoolManager receiver) {
    super(Label.CHANGE_PHONE_NUMBER, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    Form f = new Form();
    Input<Int> phoneNumber = f.addIntegerInput(Message.requestPhoneNumber());
    f.parse();

    try {
      _receiver.changePhoneNumber(phoneNumber.value());
    }
    catch (UnsupportedOperationException e) {
      _display.popup("Operação não suportada.");
    }
  }
}
