package sth;

import java.io.IOException;
import java.lang.UnsupportedOperationException;
import java.util.Collection;
import java.util.Map;

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
  private int _loggedId = -1;

  //FIXME implement constructors if needed
  
  private int getLoggedId() { return _loggedId; }
  private void setLoggedId(int id) { _loggedId = id; }
  private Person getLoggedIn() { return _school.getPersonById(getLoggedId()); }

  
  /**
   * @param datafile
   * @throws ImportFileException
   */
  public void importFile(String datafile)
    throws ImportFileException {
    try {
      _school.importFile(datafile);
    } catch (IOException | BadEntryException e) {
      throw new ImportFileException(e);
    }
  }

  /**
   * @param datafile
   */
  public void saveToFile(String datafile)
    throws IOException {
    _school.saveToFile(datafile);
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
      throw new NoSuchPersonIdException();
  }

  public void logout()
    throws NoSuchPersonIdException {

    setLoggedId(-1);
  }

  /**
   * @return true when the currently logged in person is an administrative
   */
  public boolean hasAdministrative() {
    if (getLoggedId() != -1)
      return _school.isAdministrative(login());
    else
      return false;
  }

  /**
   * @return true when the currently getLogged in person is a professor
   */
  public boolean hasProfessor() {
    if (getLoggedId() != -1)
      return _school.isAdministrative(getLoggedId());
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

  public void changePhoneNumber(int newNumber)
    throws UnsupportedOperationException {
    Person p = getLoggedIn();
    if (p != null) // in principle, the logged in person exists; this is being over cautious
      p.changePhoneNumer(newNumber);
    else
      throw new RuntimeException(); // FIXME
  }

  public Collection<Person> searchPerson(String name)
    throws UnsupportedOperationException {
    return _school.getPersonByName(name);
  }

  public Collection<Person> allPersons() {
    return _school.people(); 
  }

  // missing project description ?
  public void createProject(String discipline, String projectName)
    throws UnsupportedOperationException {
    Discipline d = getDiscipline(discipline);
    if (d == null) throw new RuntimeException(); // FIXME
    d.addProject(projectName);
  }

  public Collection<Student> getDisciplineStudents(String discipline)
    throws UnsupportedOperationException {
    Discipline d = getDiscipline(discipline);
    if (d == null) throw new RuntimeException(); // FIXME
    return d.getStudents();
  }

  // string or projectSubmission ??? FIXME
  public Map<Student, String> projectSubmissions(String discipline, String projectName)
    throws UnsupportedOperationException, NoSuchProjectException {

    Discipline d = getDiscipline(discipline);
    if (d == null) throw new RuntimeException(); // FIXME
    Project p = d.getProject(projectName);
    if (p == null) throw new NoSuchProjectException();

    return p.getSumbissions();
  }

  public void closeProject(String discipline, String projectName)
    throws UnsupportedOperationException, NoSuchProjectException {

    Discipline d = getDiscipline(discipline);
    if (d == null) throw new RuntimeException(); // FIXME
    Project p = d.getProject(projectName);
    if (p == null) throw new NoSuchProjectException();
    p.close();
  }

  public void answerSurvey(String discipline, String project, int hours, String comment)
    throws UnsupportedOperationException {

    Discipline d = getDiscipline(discipline);
    if (d == null) throw new RuntimeException(); // FIXME
    Project p = d.getProject(projectName);
    if (p == null) throw new NoSuchProjectException();

    if (p.hasSurvey) {
      Survey s = p.getSurvey();
      s.addResponse(new SurveyResponse(getLoggedIn(), hours, comment));
    }
  }

  public void deliverProject(String discipline, String project, String comment)
    throws UnsupportedOperationException {
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

  public String getFilename() 
    throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  public Person getLoggedIn()
    throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  public void createSurvey(String discipline, String project)
    throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  public void finishSurvey(String discipline, String project)
    throws UnsupportedOperationException {
      throw new UnsupportedOperationException();
    }

  public void openSurvey(String discipline, String project)
    throws UnsupportedOperationException {
      throw new UnsupportedOperationException();
    }

  public void closeSurvey(String discipline, String project)
    throws UnsupportedOperationException {
      throw new UnsupportedOperationException();
    }

  public void cancelSurvey(String discipline, String project)
    throws UnsupportedOperationException {
      throw new UnsupportedOperationException();
    }

  // more to do
}
