package sth;

import java.io.IOException;
import java.io.Serializable;
import java.lang.UnsupportedOperationException;
import java.util.List;

import sth.exceptions.BadEntryException;
//import sth.exceptions.InvalidCourseSelectionException;
import sth.exceptions.NoSuchPersonIdException;

/**
 * School implementation.
 */
public class School implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201810051538L;

  //FIXME define object fields (attributes and, possibly, associations)

  //FIXME implement constructors if needed
  
  /**
   * @param filename
   * @throws BadEntryException
   * @throws IOException
   */
  void importFile(String filename) throws IOException, BadEntryException, UnsupportedOperationException {
    //FIXME implement text file reader
    throw new UnsupportedOperationException();
  }
  
  void saveToFile(String filename) throws IOException, UnsupportedOperationException  {
    //FIXME implement text file writer
    throw new UnsupportedOperationException();
  }

  public List<Person> people() throws UnsupportedOperationException {
    //FIXME implement people getter
    throw new UnsupportedOperationException();
  }

  //FIXME implement other methods

}
