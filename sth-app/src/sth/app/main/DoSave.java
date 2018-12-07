package sth.app.main;

import java.io.IOException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input; 
import sth.SchoolManager;
import sth.exceptions.ImportFileException;

/**
 * 4.1.1. Save to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<SchoolManager> {

  Input<String> _filename;
  /**
   * @param receiver
   */
  public DoSave(SchoolManager receiver) {
    super(Label.SAVE, receiver);
    if (!receiver.hasFilename()) 
      _filename = _form.addStringInput(Message.newSaveAs());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    if (!_receiver.hasFilename()) {
      _form.parse();
      String filename = _filename.value();

      try {
        _receiver.save(filename);
      }
      catch (IOException e) {
        _display.popup(e.getMessage());
      }
    }
    else {
      try {
        _receiver.save();
      }
      catch (IOException e) {
        _display.popup(e.getMessage());
      }
    }
  }

}
