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

  private Student getRepresentativeLoggedIn() { return _school.getRepresentativeById(getLoggedId()); }
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
      throw new FileNotFoundException(e.getMessage()); // do not convert FileNotFound to ImportFile
    }
    catch (IOException | ClassNotFoundException e) {
      throw new ImportFileException(e); 
    }

    if (!newSchool.lookupId(getLoggedId())) 
      throw new NoSuchPersonIdException(getLoggedId());

    _school = newSchool;
    _filename = datafile;
    _needUpdate = true;
    return getLoggedIn().flushNotifications();
  }

  /**
   * @param id
   * @throws NoSuchPersonIdException
   */
  public Collection<SurveyNotification> login(int id)
    throws NoSuchPersonIdException {
    if (!_school.lookupId(id)) throw new NoSuchPersonIdException(id);

    setLoggedId(id);
    return getLoggedIn().flushNotifications();
  }

  public void logout() {
    setLoggedId(-1);
  }

  public String getFilename() {
    return _filename;
  }

  public boolean hasFilename() {
    return _filename != null;
  }


  public Person getLoggedIn() {
    return _school.getPersonById(getLoggedId()); 
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

  public void createProject(String disciplineName, String projectName) 
      throws DisciplineNotFoundException, ProjectAlreadyExistsException {
    Professor prof = getProfessorLoggedIn();
    Discipline d = prof.discipline(disciplineName);
    d.addProject(projectName);
    _needUpdate = true;
  }

  public Collection<Student> disciplineStudents(String disciplineName)
    throws DisciplineNotFoundException {
    Professor prof = getProfessorLoggedIn();
    Discipline d = prof.discipline(disciplineName);
    return d.students();
  }

  public Collection<String> projectSubmissions(String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException {

    Professor prof = getProfessorLoggedIn();
    Project p = prof.project(disciplineName, projectName);
    return p.getSubmissions();
  }

  public void closeProject(String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException {

    Professor prof = getProfessorLoggedIn();
    Project p = prof.project(disciplineName, projectName);
    p.close();
    _needUpdate = true;
  }

  public void deliverProject(String disciplineName, String projectName, String submission) 
    throws ProjectNotFoundException, DisciplineNotFoundException, ProjectNotOpenException {
    Student student = getStudentLoggedIn();
    student.submitProject(disciplineName, projectName, submission); 
    _needUpdate = true;
  }

  public void createSurvey(String disciplineName, String projectName) 
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyAlreadyCreatedException {
    Student rep = getRepresentativeLoggedIn();
    Project p = rep.getCourseProject(disciplineName, projectName);
    if (!p.isOpen()) throw new ProjectNotFoundException(disciplineName, projectName);
    p.createSurvey();
    _needUpdate = true;
  }

  public void finishSurvey(String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException, IllegalSurveyFinishException {
    Student rep = getRepresentativeLoggedIn();
    Survey s = rep.getCourseSurvey(disciplineName, projectName);
    s.finish();
    _needUpdate = true;
  }

  public void openSurvey(String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException, IllegalSurveyOpenException {
    Student rep = getRepresentativeLoggedIn();
    Survey s = rep.getCourseSurvey(disciplineName, projectName);
    s.open();
    _needUpdate = true;
  }

  public void closeSurvey(String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException , IllegalSurveyCloseException {
    Student rep = getRepresentativeLoggedIn();
    Survey s = rep.getCourseSurvey(disciplineName, projectName);
    s.close();
    _needUpdate = true;
  }

  public void cancelSurvey(String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException, SurveyNotEmptyException, FinishedSurveyException {
    Student rep = getRepresentativeLoggedIn();
    Survey s = rep.getCourseSurvey(disciplineName, projectName);
    s.cancel();
    _needUpdate = true;
  }

  public void answerSurvey(String disciplineName, String projectName, int hours, String comment)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException {
    Student student = getStudentLoggedIn();
    Survey s = student.survey(disciplineName, projectName);
    s.addResponse(getStudentLoggedIn(), hours, comment);
    _needUpdate = true;
  }

  public Collection<Survey> disciplineSurveys(String disciplineName) 
    throws DisciplineNotFoundException {
    Student rep = getRepresentativeLoggedIn();
    Discipline d = rep.getCourseDiscipline(disciplineName);
    return d.surveys();
  } // FIXME

  public Survey studentGetSurvey(String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException {
    Student student = getStudentLoggedIn();
    return student.survey(disciplineName, projectName);
  }
  public Survey professorGetSurvey(String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException {
    Professor prof = getProfessorLoggedIn();
    return prof.survey(disciplineName, projectName);
  }
  // more to do
}
