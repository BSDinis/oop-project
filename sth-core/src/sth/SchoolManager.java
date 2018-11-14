package sth;

import java.io.IOException;
import java.lang.UnsupportedOperationException;
import java.util.SortedMap;
import java.util.List;

import sth.exceptions.BadEntryException;
import sth.exceptions.ImportFileException;
import sth.exceptions.NoSuchPersonIdException;
import sth.exceptions.NoSuchProjectException;

//FIXME import other classes if needed

/**
 * The façade class.
 */
public class SchoolManager {
  //FIXME add object attributes if needed
  private School _school = new School();

  //FIXME implement constructors if needed
  
  /**
   * @param datafile
   * @throws ImportFileException
   */
  public void importFile(String datafile) throws ImportFileException {
    try {
      _school.importFile(datafile);
    } catch (IOException | BadEntryException e) {
      throw new ImportFileException(e);
    }
  }

  /**
   * @param datafile
   */
  public void saveToFile(String datafile) throws IOException {
    _school.saveToFile(datafile);
  }

  /**
   * @param id
   * @throws NoSuchPersonIdException
   */
  public void login(int id) throws NoSuchPersonIdException {
    //FIXME implement method
  }

  /**
   * @return true when the currently logged in person is an administrative
   */
  public boolean hasAdministrative() {
    //FIXME implement predicate
    return false; // @strawman
  }

  /**
   * @return true when the currently logged in person is a professor
   */
  public boolean hasProfessor() {
    //FIXME implement predicate
    return false; // @strawman
  }

  /**
   * @return true when the currently logged in person is a student
   */
  public boolean hasStudent() {
    //FIXME implement predicate
    return false; // @strawman
  }

  /**
   * @return true when the currently logged in person is a representative
   */
  public boolean hasRepresentative() {
    //FIXME implement predicate
    return false; // @strawman
  }

  //FIXME implement other methods (in general, one for each command in sth-app)

  public void changePhoneNumber(int newNumber) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  public Person searchPerson(String name) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  public List<Person> allPersons(String name) {
    return _school.people(); // TODO
  }

  // missing project description ?
  public void createProject(String discipline, String projectName) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  // string or projectSubmission ??? FIXME
  public SortedMap<Student, String> projectSubmissions(String discipline, String projectName) throws UnsupportedOperationException, NoSuchProjectException {
    throw new UnsupportedOperationException();
  }

  public void closeProject(String discipline, String projectName) throws UnsupportedOperationException, NoSuchProjectException {
    throw new UnsupportedOperationException();
  }

  // more to do
}
