package sth.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ClassNotFoundException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.exceptions.ImportFileException;

/**
 * 4.1.1. Open existing document.
 */
public class DoOpen extends Command<SchoolManager> {

  /**
   * @param receiver
   */
  public DoOpen(SchoolManager receiver) {
    super(Label.OPEN, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    Form f = new Form();
    InputString filename = new InputString(f, Message.openFile());
    f.parse();
    try {
      _receiver.importFile(filename.value());
    }
    catch (FileNotFoundException e) {
      _display.popup(fileNotFound(filename.value());
    }
    catch (ImportFileException e) {
      _display.popup(e.getMessage());
    }
    catch (UnsupportedOperationException e) {
      _display.popup("Operação não suportada");
    }
  }

}
