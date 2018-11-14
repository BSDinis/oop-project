package sth.app.main;

import java.io.IOException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input; 
import sth.SchoolManager;

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
      Form f = new Form();
      Input<String> inputFilename = f.addStringInput(Message.newSaveAs());
      f.parse();
      filename = inputFilename.value();
    }

    try {
      _receiver.saveToFile(filename);
    }
    /*
    catch (FileNotFoundException e) {
      _display.popup(Message.fileNotFound(filename.value()));
    }
    FIXME ????
    */
    catch (ImportFileException e) {
      _display.popup(e.getMessage());
    }
    catch (UnsupportedOperationException e) {
      _display.popup("Operação não suportada");
    }


  }

}
