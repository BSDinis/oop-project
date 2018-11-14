package sth.app.person;

import pt.tecnico.po.ui.Command;
import sth.SchoolManager;
import sth.Person;

import sth.app.exceptions.NoSuchPersonException;

/**
 * 4.2.1. Show person.
 */
public class DoShowPerson extends Command<SchoolManager> {

  /**
   * @param receiver
   */
  public DoShowPerson(SchoolManager receiver) {
    super(Label.SHOW_PERSON, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws NoSuchPersonException {
    Person p = _receiver.getLoggedIn();
    if (p == null)
      throw new NoSuchPersonException(0); // FIXME

    _display.addLine(""+p);
  }

}
