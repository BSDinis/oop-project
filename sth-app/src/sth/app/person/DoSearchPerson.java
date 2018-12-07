package sth.app.person;

import java.util.Collection;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input; 

import sth.SchoolManager;
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
    _name = _form.addStringInput(Message.requestPersonName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    _form.parse();
    Collection<String> searchResult;
    searchResult = _receiver.getPersonDescription(_name.value());

    for (String s : searchResult) {
      _display.addLine(s); 
    }
    _display.display();
  }
}
