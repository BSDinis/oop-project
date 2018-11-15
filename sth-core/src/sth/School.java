package sth;

import java.io.IOException;
import java.io.Serializable;
import java.lang.UnsupportedOperationException;
import java.util.Collection;

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
  void importFile(String filename)
    throws IOException, BadEntryException, UnsupportedOperationException {
    //FIXME implement text file reader
    throw new UnsupportedOperationException();
  }
  
  void saveToFile(String filename)
    throws IOException, UnsupportedOperationException  {
    //FIXME implement text file writer
    throw new UnsupportedOperationException();
  }

  public boolean lookupId(int id) {
    // FIXME
    return false;
  }
  
  public boolean isAdministrative(int id)
    throws UnsupportedOperationException {
    // FIXME
    throw new UnsupportedOperationException();
  }

  public boolean isProfessor(int id)
    throws UnsupportedOperationException {
    // FIXME
    throw new UnsupportedOperationException();
  }

  public boolean isStudent(int id)
    throws UnsupportedOperationException {
    // FIXME
    throw new UnsupportedOperationException();
  }

  public boolean isRepresentative(int id)
    throws UnsupportedOperationException {
    // FIXME
    throw new UnsupportedOperationException();
  }

  public Collection<Person> people()
    throws UnsupportedOperationException {
    //FIXME implement people getter
    throw new UnsupportedOperationException();
  }

  public Person getPersonById(int id) {
    // FIXME
    return null;
  }

  public Student getStudentById(int id) {
    // FIXME
    return null;
  }

  public Professor getProfessorById(int id) {
    // FIXME
    return null;
  }

  public Collection<Person> getPersonByName(String name) {
    return null;
  }

  public Discipline getDiscipline(String name) {
    // FIXME
    return null;
  }

  //FIXME implement other methods

}
