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
  private boolean _hasFilename;
  /**
   * @param receiver
   */
  public DoOpen(SchoolManager receiver) {
    super(Label.OPEN, receiver);
    if (!(_hasFilename = receiver.hasFilename()))
      _filenameInput = _form.addStringInput(Message.openFile());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws NoSuchPersonException {
    if (!_hasFilename) {
      try {
          _form.parse();
          _receiver.load(_filenameInput.value());
      }
      catch (FileNotFoundException e) {
        _display.popup(Message.fileNotFound(_filenameInput.value()));
      }
      catch (ImportFileException e) {
        _display.popup(e.getMessage());
      }
      catch (NoSuchPersonIdException e) {
        throw new NoSuchPersonException(e.getId());
      }
    }
    else {
      try {
        _receiver.load();
      }
      catch (FileNotFoundException e) {
        _display.popup(Message.fileNotFound(_receiver.getFilename()));
      }
      catch (ImportFileException e) {
        _display.popup(e.getMessage());
      }
      catch (NoSuchPersonIdException e) {
        throw new NoSuchPersonException(e.getId());
      }
    }
  }

}
