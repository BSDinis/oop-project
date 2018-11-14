package sth.app.person;

import java.util.Collection;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input; 

import sth.SchoolManager;
import sth.Person;

/**
 * 4.2.4. Search person.
 */
public class DoSearchPerson extends Command<SchoolManager> {

  private Input<String> _name;
  
  /**
   * @param receiver
   */
  public DoSearchPerson(SchoolManager receiver) {
    super(Label.SEARCH_PERSON, receiver);
    _form = _form.addStringInput(Message.requestPersonName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    _form.parse();
    Collection<Person> searchResult;
    searchResult = _receiver.searchPerson(_name.value());

    for (Person p : searchResult) {
      _display.popup(p); // FIXME
    }
  }
}
