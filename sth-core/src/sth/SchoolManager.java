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
import sth.exceptions.SurveyException;
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

  public String getFilename() {
    return _filename;
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

  public void createProject(String discipline, String project) 
      throws DisciplineNotFoundException, ProjectAlreadyExistsException {
    Professor prof = getProfessorLoggedIn();
    Discipline d = prof.getDiscipline(discipline);
    d.addProject(project);
    _needUpdate = true;
  }

  public Collection<Student> getDisciplineStudents(String discipline)
    throws DisciplineNotFoundException {
    Professor prof = getProfessorLoggedIn();
    Discipline d = prof.getDiscipline(discipline);
    return d.getStudents();
  }

  public Map<Student, String> getProjectSubmissions(String discipline, String project)
    throws ProjectNotFoundException, DisciplineNotFoundException {

    Professor prof = getProfessorLoggedIn();
    Project p = prof.getProject(discipline, project);
    return p.getSubmissions();
  }

  public void closeProject(String discipline, String project)
    throws ProjectNotFoundException, DisciplineNotFoundException {

    Professor prof = getProfessorLoggedIn();
    Project p = prof.getProject(discipline, project);
    p.close();
    _needUpdate = true;
  }

  public void deliverProject(String discipline, String project, String submission) 
    throws ProjectNotFoundException, DisciplineNotFoundException, ProjectNotOpenException {
    Student student = getStudentLoggedIn();
    Project p = student.getProject(discipline, project);
    p.acceptSubmission(student, submission);
    _needUpdate = true;
  }

  public void createSurvey(String discipline, String project) 
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyAlreadyCreatedException {
    Student rep = getRepresentativeLoggedIn();
    Project p = rep.getCourseProject(discipline, project);
    if (!p.isOpen()) throw new ProjectNotFoundException(project);
    p.createSurvey();
    _needUpdate = true;
  }

  public void finishSurvey(String discipline, String project)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException, IllegalSurveyFinishException {
    Student rep = getRepresentativeLoggedIn();
    Survey s = rep.getCourseSurvey(discipline, project);
    s.finish();
    _needUpdate = true;
  }

  public void openSurvey(String discipline, String project)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException, IllegalSurveyOpenException {
    Student rep = getRepresentativeLoggedIn();
    Survey s = rep.getCourseSurvey(discipline, project);
    s.open();
    _needUpdate = true;
  }

  public void closeSurvey(String discipline, String project)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException , IllegalSurveyCloseException {
    Student rep = getRepresentativeLoggedIn();
    Survey s = rep.getCourseSurvey(discipline, project);
    s.close();
    _needUpdate = true;
  }

  public void cancelSurvey(String discipline, String project)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException, SurveyNotEmptyException, FinishedSurveyException {
    Student rep = getRepresentativeLoggedIn();
    Survey s = rep.getCourseSurvey(discipline, project);
    s.cancel();
    _needUpdate = true;
  }

  public void answerSurvey(String discipline, String project, int hours, String comment)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException {
    Student student = getStudentLoggedIn();
    Survey s = student.getSurvey(discipline, project);
    s.addResponse(getStudentLoggedIn(), hours, comment);
    _needUpdate = true;
  }

  public Collection<Survey> getDisciplineSurveys(String discipline) 
    throws DisciplineNotFoundException {
    Student rep = getRepresentativeLoggedIn();
    Discipline d = rep.getCourseDiscipline(discipline);
    return d.getSurveys();
  } // FIXME

  public Survey studentGetSurvey(String discipline, String project)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException {
    Student student = getStudentLoggedIn();
    return student.getSurvey(discipline, project);
  }
  public Survey professorGetSurvey(String discipline, String project)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException {
    Professor prof = getProfessorLoggedIn();
    return prof.getSurvey(discipline, project);
  }
  // more to do
}
