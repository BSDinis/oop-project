package sth;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.lang.UnsupportedOperationException;

import java.util.Collection;
import java.util.Map;

import sth.exceptions.BadEntryException;
import sth.exceptions.ImportFileException;
import sth.exceptions.NoSuchPersonIdException;
import sth.exceptions.ProjectNotFoundException;
import sth.exceptions.DisciplineNotFoundException;

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

  private Student getStudentLoggedIn() { return _school.getStudentById(getLoggedId()); }
  private Professor getProfessorLoggedIn() { return _school.getProfessorById(getLoggedId()); }

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

  public void load(String datafile) 
    throws FileNotFoundException, ImportFileException, NoSuchPersonIdException {

    School newSchool;
    try {
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(datafile));
      newSchool = (School) ois.readObject();
      ois.close();
    }
    catch (FileNotFoundException e) {
      throw new FileNotFoundException(e.getMessage()); // do not convert FileNotFound to ImportFile
    }
    catch (IOException | ClassNotFoundException e) {
      throw new ImportFileException(e); 
    }

    if (!newSchool.lookupId(getLoggedId())) 
      throw new NoSuchPersonIdException(getLoggedId());
    else {
      _school = newSchool;
      _filename = datafile;
      _needUpdate = false;
    }
  }

  /**
   * @param id
   * @throws NoSuchPersonIdException
   */
  public void login(int id)
    throws NoSuchPersonIdException {

    if (_school.lookupId(id))
      setLoggedId(id);
    else 
      throw new NoSuchPersonIdException(id);
  }

  public void logout() {
    setLoggedId(-1);
  }

  /**
   * @return true when the currently logged in person is an administrative
   */
  public boolean hasAdministrative() {
    if (getLoggedId() != -1)
      return _school.isAdministrative(getLoggedId());
    else
      return false;
  }

  /**
   * @return true when the currently getLogged in person is a professor
   */
  public boolean hasProfessor() {
    if (getLoggedId() != -1)
      return _school.isProfessor(getLoggedId());
    else
      return false;
  }

  /**
   * @return true when the currently getLogged in person is a student
   */
  public boolean hasStudent() {
    if (getLoggedId() != -1)
      return _school.isStudent(getLoggedId());
    else
      return false;
  }

  /**
   * @return true when the currently getLogged in person is a representative
   */
  public boolean hasRepresentative() {
    if (getLoggedId() != -1)
      return _school.isRepresentative(getLoggedId());
    else
      return false;
  }

  public void changePhoneNumber(String newNumber) {
    Person p = getLoggedIn();
    if (p != null) {
      // in principle, the logged in person exists; this is being over cautious
      p.changePhoneNumber(newNumber);
      _needUpdate = true;
    }
  }

  public Collection<Person> searchPerson(String name) {
    return _school.getPersonByName(name);
  }

  public Collection<Person> allPersons() {
    return _school.people(); 
  }

  public void createProject(String discipline, String projectName) throws DisciplineNotFoundException {
    Professor prof = getProfessorLoggedIn();
    Discipline d = prof.getDiscipline(discipline);
    if (d == null) throw new DisciplineNotFoundException(discipline);
    d.addProject(projectName);
    _needUpdate = true;
  }

  public Collection<Student> getDisciplineStudents(String discipline)
    throws DisciplineNotFoundException {
    Professor prof = getProfessorLoggedIn();
    Discipline d = prof.getDiscipline(discipline);
    if (d == null) throw new DisciplineNotFoundException(discipline);
    return d.getStudents();
  }

  public Map<Student, String> getProjectSubmissions(String discipline, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException {

    Professor prof = getProfessorLoggedIn();
    Discipline d = prof.getDiscipline(discipline);
    if (d == null) throw new DisciplineNotFoundException(discipline);
    Project p = d.getProject(projectName);
    if (p == null) throw new ProjectNotFoundException(projectName);

    return p.getSubmissions();
  }

  public void closeProject(String discipline, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException {

    Professor prof = getProfessorLoggedIn();
    Discipline d = prof.getDiscipline(discipline);
    if (d == null) throw new DisciplineNotFoundException(discipline);
    Project p = _school.getProject(discipline, projectName);
    if (p == null) throw new ProjectNotFoundException(projectName);
    p.close();
    _needUpdate = true;
  }

  public void answerSurvey(String discipline, String projectName, int hours, String comment)
    throws ProjectNotFoundException, DisciplineNotFoundException {
    Project p = _school.getProject(discipline, projectName);
    if (p == null) throw new ProjectNotFoundException(projectName);

    if (p.hasSurvey()) {
      Survey s = p.getSurvey();
      s.addResponse(getStudentLoggedIn(), hours, comment);
      _needUpdate = true;
    }
  }

  public void deliverProject(String discipline, String project, String comment)
    throws UnsupportedOperationException {
    _needUpdate = true;
    throw new UnsupportedOperationException();
  }

  public Survey getSurvey(String discipline, String project) 
    throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  public Project getProject(String discipline, String project) 
    throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  public Collection<Survey> getProjectSurveys(String discipline, String project) 
    throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  public Collection<Survey> getDisciplineSurveys(String discipline) 
    throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  public String getFilename() {
    return _filename;
  }

  public Person getLoggedIn() {
    return _school.getPersonById(getLoggedId()); 
  }

  public void createSurvey(String discipline, String project)
    throws UnsupportedOperationException {
    _needUpdate = true;
    throw new UnsupportedOperationException();
  }

  public void finishSurvey(String discipline, String project)
    throws UnsupportedOperationException {
    _needUpdate = true;
    throw new UnsupportedOperationException();
  }

  public void openSurvey(String discipline, String project)
    throws UnsupportedOperationException {
    _needUpdate = true;
    throw new UnsupportedOperationException();
  }

  public void closeSurvey(String discipline, String project)
    throws UnsupportedOperationException {
    _needUpdate = true;
    throw new UnsupportedOperationException();
  }

  public void cancelSurvey(String discipline, String project)
    throws UnsupportedOperationException {
    _needUpdate = true;
    throw new UnsupportedOperationException();
  }

  // more to do
}
