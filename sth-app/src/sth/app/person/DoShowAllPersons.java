package sth.app.person;

import java.util.Collection;
import pt.tecnico.po.ui.Command;
import sth.SchoolManager;
import sth.Person;

/**
 * 4.2.3. Show all persons.
 */
public class DoShowAllPersons extends Command<SchoolManager> {


  /**
   * @param receiver
   */
  public DoShowAllPersons(SchoolManager receiver) {
    super(Label.SHOW_ALL_PERSONS, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    Collection<Person> people = _receiver.allPersons();
    for (Person p : people) {
      _display.addLine(p.toString()); 
    }
    _display.display();
  }

}
