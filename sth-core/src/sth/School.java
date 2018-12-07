package sth;

import java.io.IOException;
import java.io.Serializable;

import java.util.Comparator;

import java.util.Map;
import java.util.TreeMap;

import java.util.Collection;
import java.util.TreeSet;
import java.util.List;
import java.util.LinkedList;

import java.util.Locale;
import java.text.Collator;

import java.io.BufferedReader;
import java.io.FileReader;

import sth.exceptions.BadEntryException;
import sth.exceptions.DisciplineNotFoundException;
import sth.exceptions.DuplicatePersonException;
import sth.exceptions.DuplicateCourseException;

import sth.exceptions.ProjectNotOpenException;
import sth.exceptions.ProjectNotFoundException;
import sth.exceptions.ProjectAlreadyExistsException;

import sth.exceptions.IllegalSurveyCloseException;
import sth.exceptions.IllegalSurveyFinishException;
import sth.exceptions.IllegalSurveyOpenException;
import sth.exceptions.SurveyAlreadyCreatedException;
import sth.exceptions.FinishedSurveyException;
import sth.exceptions.SurveyNotEmptyException;
import sth.exceptions.SurveyNotFoundException;


/**
 * School is where the information is stored and manipulated directly
 */
class School implements Serializable {

  /**
   * Id to assign comming numbers
   */
  private int _currentId = 100000; // Initial ID

  /**
   * Association between id's and students
   */
  private Map<Integer, Student> _students = new TreeMap<>();

  /**
   * Association between id's and professors
   */
  private Map<Integer, Professor> _professors = new TreeMap<>();

  /**
   * Association between id's and staffers
   */
  private Map<Integer, Staffer> _staffers = new TreeMap<>();

  /**
   * List of courses
   */
  private Map<String, Course> _courses = new TreeMap<>();


  /**
   * Reset the school to its original settings
   */
  private void reset() {
    _currentId = 100000;
    _students = new TreeMap<>();
    _professors = new TreeMap<>();
    _staffers = new TreeMap<>();
    _courses = new TreeMap<>();
  }

  /**
   * Add a student to the school
   *
   * @param Student
   * @return Student: allow for `Student s = school.addStudent(new Student(...)) constructs`
   * @throws DuplicatePersonException
   */
  Student addStudent(Student s) 
    throws DuplicatePersonException {
    if (lookupId(s.id())) throw new DuplicatePersonException(s.id());
    _students.put(s.id(), s);
    return s;
  }

  /**
   * Add a professor to the school
   *
   * @param Professor
   * @return Professor: allow for `Professor s = school.addProfessor(new Professor(...)) constructs`
   * @throws DuplicatePersonException
   */
  Professor addProfessor(Professor p) 
    throws DuplicatePersonException {
    if (lookupId(p.id())) throw new DuplicatePersonException(p.id());
    _professors.put(p.id(), p);
    return p;
  }

  /**
   * Add a staffer to the school
   *
   * @param Staffer
   * @return Staffer: allow for `Staffer s = school.addStaffer(new Staffer(...)) constructs`
   * @throws DuplicatePersonException
   */
  Staffer addStaffer(Staffer s) 
    throws DuplicatePersonException {
    if (lookupId(s.id())) throw new DuplicatePersonException(s.id());
    _staffers.put(s.id(), s);
    return s;
  }

  /**
   * Add a course to the school
   *
   * @param Course
   * @return Course: allow for `Course s = school.addCourse(new Course(...)) constructs`
   * @throws DuplicateCourseException
   */
  Course addCourse(Course c) 
    throws DuplicateCourseException {
    if (hasCourse(c.name())) throw new DuplicateCourseException(c.name());
    _courses.put(c.name(), c);
    return c;
  }


  /**
   * Reads from inputfile
   *
   * @param filename
   * @throws BadEntryException
   * @throws IOException
   */
  void importFile(String filename)
    throws IOException, BadEntryException {
    reset();
    BufferedReader in = new BufferedReader(new FileReader(filename));
    PersonParser p = new PersonParser(this, in);
    while (p.parsePerson()); // parse all people
    in.close();
  }
  
  /**
   * Check if an id is taken
   *
   * @param id 
   * @return whether an id is taken or not
   */
  boolean lookupId(int id) {
    return _students.containsKey(id) || _professors.containsKey(id) || _staffers.containsKey(id);
  }
  
  /**
   * Check if an id corresponds to a staffer or not
   *
   * @param id 
   * @return whether an id is a staffer's id
   */
  boolean isAdministrative(int id) {
    return _staffers.containsKey(id);
  }

  /**
   * Check if an id corresponds to a professor or not
   *
   * @param id 
   * @return whether an id is a professor's id
   */
  boolean isProfessor(int id) {
    return _professors.containsKey(id);
  }

  /**
   * Check if an id corresponds to a student or not
   *
   * @param id 
   * @return whether an id is a student's id
   */
  boolean isStudent(int id) {
    return _students.containsKey(id);
  }

  /**
   * Check if an id corresponds to a representative or not
   *
   * @param id 
   * @return whether an id is a representative's id
   */
  boolean isRepresentative(int id) {
    if (!isStudent(id)) return false;

    Collection<Course> courseCollection = _courses.values();
    for (Course c : courseCollection)
      if (c.hasRepresentative(id)) return true;

    return false;
  }

  /**
   * Get all the people, ordered by id
   *
   * @return Collection of people
   */
  Collection<Person> people() {
    Collection<Person> allPeople = new TreeSet<Person>();
    allPeople.addAll(_students.values());
    allPeople.addAll(_staffers.values());
    allPeople.addAll(_professors.values());
    return allPeople;
  }

  /**
   * Get a person by their id
   *
   * @return Person
   */
  Person getPersonById(int id) {
    Person p;
    p = getStafferById(id);
    if (p != null) return p;

    p = getStudentById(id);
    if (p != null) return p;

    p = getProfessorById(id);
    return p;
  }

  /**
   * Flush someone's notifications
   *
   * @return Collection<SurveyNotification>
   */
  Collection<SurveyNotification> flushPersonNotifications(int id) {
    Person p = getPersonById(id);
    if (p == null) return null;
    return p.flushNotifications();
  }

  /**
   * Get description of a person 
   *
   * @return String
   */
  String getPersonDescription(int id) {
    Person p = getPersonById(id);
    if (p == null) return null;
    return p.toString();
  }

  /**
   * Get a staffer by their id
   *
   * @return Staffer
   */
  Staffer getStafferById(int id) {
    return _staffers.get(id);
  }

  /**
   * Change person's phone number
   *
   * @return boolean success of change
   */
  boolean changePhoneNumber(int id, String newNumber) {
    Person p = getPersonById(id);
    if (p != null) {
      // in principle, the logged in person exists; this is being over cautious
      p.changePhoneNumber(newNumber);
      return true;
    }
    return false;
  }
  /**
   * Get a representative by their id
   *
   * @return Student
   */
  Student getRepresentativeById(int id) {
    if (!isRepresentative(id)) return null;
    return _students.get(id);
  }

  /**
   * Get a student by their id
   *
   * @return Student
   */
  Student getStudentById(int id) {
    return _students.get(id);
  }

  /**
   * Get a professor by their id
   *
   * @return Professor
   */
  Professor getProfessorById(int id) {
    return _professors.get(id);
  }

  /**
   * Check whether a school has a course
   *
   * @return boolean
   */
  boolean hasCourse(String name) { return _courses.containsKey(name); }

  /**
   * Get a course by its name
   *
   * @return Course
   */
  Course getCourseByName(String name) { return _courses.get(name); }

  /**
   * Get group of people who have a certain name
   *
   * @return Collection of String
   */
  Collection<Person> getPersonByName(String name) {
    // using tree set for ordering
    Collection<Person> peopleSet = new TreeSet<>(new Comparator<Person>() {
      public int compare(Person p1, Person p2) {
        Collator c = Collator.getInstance(Locale.getDefault());
        return c.compare(p1.name(), p2.name());
      }
    });

    for (Person p : people()) {
      if (p.name().contains(name))
        peopleSet.add(p);
    }

    return peopleSet;
  }

  /**
   * Get list of students in a discipline (has to be a professor)
   *
   * @param id
   * @param disciplineName
   */
  Collection<Student> disciplineStudents(int id, String disciplineName)
    throws DisciplineNotFoundException {
    Professor prof = getProfessorById(id); // FIXME 
    Discipline d = prof.getDiscipline(disciplineName);
    return d.getStudents();
  }


  /**
   * Create a project for a user (has to be a professor)
   *
   * @param id
   * @param disciplineName
   * @param projectName
   */
  void createProject(int id, String disciplineName, String projectName) 
      throws DisciplineNotFoundException, ProjectAlreadyExistsException {
    Professor prof = getProfessorById(id); // FIXME
    Discipline d = prof.getDiscipline(disciplineName);
    d.addProject(projectName);
  }


  /**
   * Get collection of students in a discipline (has to be a professor)
   *
   * @param id
   * @param disciplineName
   * @param projectName
   */
  Collection<String> projectSubmissions(int id, String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException {
    Professor prof = getProfessorById(id);
    Project p = prof.getProject(disciplineName, projectName);
    return p.getSubmissions();
  }

  /**
   * Close a project
   *
   * @param id
   * @param disciplineName
   * @param projectName
   */
  void closeProject(int id, String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException {

    Professor prof = getProfessorById(id);
    Project p = prof.getProject(disciplineName, projectName);
    p.close();
  }

  /**
   * Create a project
   *
   * @param id
   * @param disciplineName
   * @param projectName
   */
  void createSurvey(int id, String disciplineName, String projectName) 
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyAlreadyCreatedException {
    Student rep = getRepresentativeById(id);
    Course course = rep.getCourse();
    Project p = course.getProject(disciplineName, projectName);
    if (!p.isOpen()) throw new ProjectNotFoundException(disciplineName, projectName);
    p.createSurvey();
  }


  /**
   * Open a Survey
   *
   * @param id
   * @param disciplineName
   * @param projectName
   */
  void openSurvey(int id, String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException, IllegalSurveyOpenException {
    Student rep = getRepresentativeById(id);
    Course course = rep.getCourse();
    Survey s = course.getSurvey(disciplineName, projectName);
    s.open();
  }


  /**
   * Close a Survey
   *
   * @param id
   * @param disciplineName
   * @param projectName
   */
  void closeSurvey(int id, String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException , IllegalSurveyCloseException {
    Student rep = getRepresentativeById(id);
    Course course = rep.getCourse();
    Survey s = course.getSurvey(disciplineName, projectName);
    s.close();
  }


  /**
   * Cancel a Survey
   *
   * @param id
   * @param disciplineName
   * @param projectName
   */
  void cancelSurvey(int id, String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException, SurveyNotEmptyException, FinishedSurveyException {
    Student rep = getRepresentativeById(id);
    Course course = rep.getCourse();
    Survey s = course.getSurvey(disciplineName, projectName);
    s.cancel();
  }


  /**
   * Finish a Survey
   *
   * @param id
   * @param disciplineName
   * @param projectName
   */
  void finishSurvey(int id, String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException, IllegalSurveyFinishException {
    Student rep = getRepresentativeById(id);
    Course course = rep.getCourse();
    Survey s = course.getSurvey(disciplineName, projectName);
    s.finish();
  }


  /**
   * Get all the surveys from a discipline
   *
   * @param id
   * @param disciplineName
   */
  Collection<Survey> disciplineSurveys(int id, String disciplineName) 
    throws DisciplineNotFoundException {
    Student rep = getRepresentativeById(id);
    Course course = rep.getCourse();
    Discipline d = course.getDiscipline(disciplineName);
    return d.getSurveys();
  } 


  /**
   * Submit a project
   *
   * @param id
   * @param disciplineName
   * @param projectName
   * @param submission
   */
  void deliverProject(int id, String disciplineName, String projectName, String submission) 
    throws ProjectNotFoundException, DisciplineNotFoundException, ProjectNotOpenException {
    Student student = getStudentById(id);
    student.submitProject(disciplineName, projectName, submission); 
  }

  /**
   * Answer aa survey
   *
   * @param id
   * @param disciplineName
   * @param projectName
   * @param hours
   * @param comment
   */
  void answerSurvey(int id, String disciplineName, String projectName, int hours, String comment)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException {
    Student student = getStudentById(id);
    Survey s = student.getSurvey(disciplineName, projectName);
    s.addResponse(getStudentById(id), hours, comment);
  }


  /**
   * Get a survey
   *
   * @param id
   * @param disciplineName
   * @param projectName
   */
  Survey getSurvey(int id, String disciplineName, String projectName)
    throws ProjectNotFoundException, DisciplineNotFoundException, SurveyNotFoundException {
    PersonWithDisciplines person = null;
    if (isStudent(id)) person = getStudentById(id);
    else if (isProfessor(id)) person = getProfessorById(id);
    else throw new SurveyNotFoundException(disciplineName, projectName); // this should never happen

    return person.getSurvey(disciplineName, projectName);
  }
}
