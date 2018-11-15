package sth.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ClassNotFoundException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Form;
import sth.SchoolManager;
import sth.exceptions.ImportFileException;

/**
 * 4.1.1. Open existing document.
 */
public class DoOpen extends Command<SchoolManager> {
  
  
  private Input<String> _filename;
  /**
   * @param receiver
   */
  public DoOpen(SchoolManager receiver) {
    super(Label.OPEN, receiver);
    _filename = _form.addStringInput(Message.openFile());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    _form.parse();
    try {
      _receiver.importFile(_filename.value());
    }
    /*
    catch (FileNotFoundException e) {
      _display.popup(Message.fileNotFound(_filename.value()));
    }

    FIXME */
    catch (ImportFileException e) {
      _display.popup(e.getMessage());
    }
    catch (UnsupportedOperationException e) {
      _display.popup("Operação não suportada");
    } // FIXME: remove
  }

}
