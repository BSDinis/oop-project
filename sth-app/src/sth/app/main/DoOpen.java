package sth.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ClassNotFoundException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Form;

import sth.SchoolManager;
import sth.exceptions.ImportFileException;
import sth.exceptions.NoSuchPersonIdException;
import sth.app.exceptions.NoSuchPersonException;

/**
 * 4.1.1. Open existing document.
 */
public class DoOpen extends Command<SchoolManager> {
  
  
  private Input<String> _filenameInput;
  private String _filename = null;
  /**
   * @param receiver
   */
  public DoOpen(SchoolManager receiver) {
    super(Label.OPEN, receiver);
    _filename = receiver.getFilename();
    if (_filename == null)
      _filenameInput = _form.addStringInput(Message.openFile());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws NoSuchPersonException {
    if (_filename == null) {
      _form.parse();
      _filename = _filenameInput.value();
    }
    try {
      _receiver.load(_filename);
    }
    catch (FileNotFoundException e) {
      _display.popup(Message.fileNotFound(_filename));
    }
    catch (ImportFileException e) {
      _display.popup(e.getMessage());
    }
    catch (NoSuchPersonIdException e) {
      throw new NoSuchPersonException(e.getId());
    }
  }

}
