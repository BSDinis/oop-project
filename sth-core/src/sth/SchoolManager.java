package sth;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.Collection;
import java.util.Map;

import sth.exceptions.BadEntryException;
import sth.exceptions.ImportFileException;
import sth.exceptions.NoSuchPersonIdException;
import sth.exceptions.ProjectNotOpenException;
import sth.exceptions.ProjectNotFoundException;
import sth.exceptions.ProjectAlreadyExistsException;
import sth.exceptions.DisciplineNotFoundException;

import sth.exceptions.IllegalSurveyCloseException;
import sth.exceptions.IllegalSurveyFinishException;
import sth.exceptions.IllegalSurveyOpenException;
import sth.exceptions.SurveyAlreadyCreatedException;
import sth.exceptions.FinishedSurveyException;
import sth.exceptions.SurveyNotEmptyException;
import sth.exceptions.SurveyNotFoundException;


/*
 * Note to future self:
 * The school manager returns objects to the school.
 * This is OK: the objects' public interface DOES NOT ALTER IT state
 * This is important; any future changes should keep that in mind
 *
 * Also this puts the burden of formatting the output in the app (where it should be)
 * Some objects have toString functions that may (coincidentally) coincide with the
 *   app's formatting requirements.
 *
 * This is whishful thinking ;).
 * If more flexibility were to be required, visitors could be created (see SurveyPrinter.java)
 */

/**
 * The façade class.
 */
public class SchoolManager {
  private School _school = new School();
  private int _loggedId = -1;
  private boolean _needUpdate = false;
  private String _filename = null;

  private int getLoggedId() { return _loggedId; }
  private void setLoggedId(int id) { _loggedId = id; }

  /**
   * @param datafile
   * @throws ImportFileException
   */
  public void importFile(String datafile)
    throws ImportFileException {
    try {
      _school.importFile(datafile);
      _needUpdate = false;
    } catch (IOException | BadEntryException e) {
      throw new ImportFileException(e);
    }
  }

  public void save() 
    throws IOException {
    save(getFilename());
  }

  public void save(String datafile) 
    throws IOException {
    if (_filename == null || !_filename.equals(datafile) || _needUpdate) {
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(datafile));
      oos.writeObject(_school);
      oos.close();
      _filename = datafile;
      _needUpdate = false;
    }
  }

  public Collection<SurveyNotification> load() 
    throws FileNotFoundException, ImportFileException, NoSuchPersonIdException {
    return load(getFilename());  
  }

  public Collection<SurveyNotification> load(String datafile) 
    throws FileNotFoundException, ImportFileException, NoSuchPersonIdException {

    School newSchool;
    try {
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(datafile));
      newSchool = (School) ois.readObject();
      ois.close();
    }
    catch (FileNotFoundException e) {
      throw new FileNotFoundException(datafile); // send filename that caused
    }
    catch (IOException | ClassNotFoundException e) {
      throw new ImportFileException(e); 
    }

    if (!newSchool.lookupId(getLoggedId())) 
      throw new NoSuchPersonIdException(getLoggedId());

    _school = newSchool;
    _filename = datafile;
    Collection<SurveyNotification> notifs = _school.flushPersonNotifications(getLoggedId());
    _needUpdate |= notifs != null; // going for strictly necessary updates
    return notifs;
  }

  /**
   * @param id
   * @throws NoSuchPersonIdException
   */
  public Collection<SurveyNotification> login(int id)
    throws NoSuchPersonIdException {
    if (!_school.lookupId(id)) throw new NoSuchPersonIdException(id);

    setLoggedId(id);
    Collection<SurveyNotification> notifs = _school.flushPersonNotifications(id);
    _needUpdate |= notifs != null; // given the current specification people never have notfis on login; you can never be to careful though
    return notifs;
  }

  public void logout() {
    setLoggedId(-1);
  }

  String getFilename() {
    return _filename;
  }

  public boolean hasFilename() {
    return _filename != null;
  }

  public String getLoggedPersonDescription() {
    return _school.getPersonDescription(getLoggedId());
  }

  public Person getPersonLoggedIn() {
    return _school.getPersonById(getLoggedId());
  }

  /**
   * @return true when the currently logged in person is an administrative
   */
  public boolean hasAdministrative() {
    return getLoggedId() != -1 && _school.isAdministrative(getLoggedId());
  }

  /**
   * @return true when the currently getLogged in person is a professor
   */
  public boolean hasProfessor() {
    return getLoggedId() != -1 && _school.isProfessor(getLoggedId());
  }

  /**
   * @return true when the currently getLogged in person is a student
   */
  public boolean hasStudent() {
    return getLoggedId() != -1 && _school.isStudent(getLoggedId());
  }

  /**
   * @return true when the currently getLogged in person is a representative
   */
  public boolean hasRepresentative() {
    return getLoggedId() != -1 && _school.isRepresentative(getLoggedId());
  }

  /* =====================================
   *           Generic methods
   * =====================================*/
  public void changePhoneNumber(String newNumber) {
    _needUpdate |= _school.changePhoneNumber(getLoggedId(), newNumber);
  }

  public Collection<Person> getPerson(String name) {
    return _school.getPersonByName(name);
  }

  public Collection<Person> allPersons() {
    return _school.people(); 
  }

  /* =====================================
   *           Professor methods
   * =====================================*/

  public Collection<Student> disciplineStudents(String disciplineName)
    throws DisciplineNotFoundException {
    return _school.disciplineStudents(getLoggedId(), disciplineName);
  }

  public void createProject(String disciplineName, String projectName) 
      throws DisciplineNotFoundException, ProjectAlreadyExistsException {
    _school.createProject(getLoggedId(), disciplineName, projectName);
    _needUpdate = true;
  }

  public void closeProject(String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException {

    _school.closeProject(getLoggedId(), disciplineName, projectName);
    _needUpdate = true;
  }

  public Collection<String> projectSubmissions(String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException {
    return _school.projectSubmissions(getLoggedId(), disciplineName, projectName);
  }

  /* =====================================
   *        Representative methods
   * =====================================*/

  public void createSurvey(String disciplineName, String projectName) 
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyAlreadyCreatedException {
    _school.createSurvey(getLoggedId(), disciplineName, projectName);
    _needUpdate = true;
  }

  public void finishSurvey(String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException, IllegalSurveyFinishException {
    _school.finishSurvey(getLoggedId(), disciplineName, projectName);
    _needUpdate = true;
  }

  public void openSurvey(String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException, IllegalSurveyOpenException {
    _school.openSurvey(getLoggedId(), disciplineName, projectName);
    _needUpdate = true;
  }

  public void closeSurvey(String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException , IllegalSurveyCloseException {
    _school.closeSurvey(getLoggedId(), disciplineName, projectName);
    _needUpdate = true;
  }

  public void cancelSurvey(String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException, SurveyNotEmptyException, FinishedSurveyException {
    _school.cancelSurvey(getLoggedId(), disciplineName, projectName);
    _needUpdate = true;
  }

  public Collection<Survey> disciplineSurveys(String disciplineName) 
    throws DisciplineNotFoundException {
    return _school.disciplineSurveys(getLoggedId(), disciplineName);
  } 

  /* =====================================
   *           Student methods
   * =====================================*/

  public void deliverProject(String disciplineName, String projectName, String submission) 
    throws ProjectNotFoundException, DisciplineNotFoundException, ProjectNotOpenException {
    _school.deliverProject(getLoggedId(), disciplineName, projectName, submission);
    _needUpdate = true;
  }

  public void answerSurvey(String disciplineName, String projectName, int hours, String comment)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException {
    _school.answerSurvey(getLoggedId(), disciplineName, projectName, hours, comment);
    _needUpdate = true;
  }

  // this is for all
  public Survey getSurvey(String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException {
    return _school.getSurvey(getLoggedId(), disciplineName, projectName);
  }
}
