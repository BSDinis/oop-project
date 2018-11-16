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
  /**
   * @param receiver
   */
  public DoSave(SchoolManager receiver) {
    super(Label.SAVE, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    String filename = _receiver.getFilename();

    if (filename == null) {
      Input<String> inputFilename = _form.addStringInput(Message.newSaveAs());
      _form.parse();
      filename = inputFilename.value();
    }

    try {
      _receiver.saveToFile(filename);
    }
    catch (IOException e) {
      _display.popup(e.getMessage());
    }
  }

}
