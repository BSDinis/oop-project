package sth.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ClassNotFoundException;

import java.util.Collection;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Form;

import sth.SchoolManager;
import sth.exceptions.ImportFileException;
import sth.exceptions.NoSuchPersonIdException;
import sth.app.exceptions.NoSuchPersonException;

import sth.SurveyNotification;
import sth.SurveyNotificationPrinter;
import sth.app.printers.SurveyNotificationBasicPrinter;

/**
 * 4.1.1. Open existing document.
 */
public class DoOpen extends Command<SchoolManager> {
  
  
  private Input<String> _filenameInput;
  /**
   * @param receiver
   */
  public DoOpen(SchoolManager receiver) {
    super(Label.OPEN, receiver);
    if (!receiver.hasFilename())
      _filenameInput = _form.addStringInput(Message.openFile());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws NoSuchPersonException {
    Collection<SurveyNotification> notifs = null;
    if (!_receiver.hasFilename()) {
      try {
          _form.parse();
          notifs = _receiver.load(_filenameInput.value());
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
        notifs = _receiver.load();
      }
      catch (FileNotFoundException e) {
        _display.popup(Message.fileNotFound(e.getMessage()));
      }
      catch (ImportFileException e) {
        _display.popup(e.getMessage());
      }
      catch (NoSuchPersonIdException e) {
        throw new NoSuchPersonException(e.getId());
      }
    }

    SurveyNotificationPrinter printer = new SurveyNotificationBasicPrinter();
    _display.addLine(printer.print(notifs));
    _display.display();
  }

}
