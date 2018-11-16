package sth;

import java.io.IOException;
import java.io.Serializable;
import java.lang.UnsupportedOperationException;

import java.util.Comparator;

import java.util.Map;
import java.util.TreeMap;

import java.util.Collection;
import java.util.TreeSet;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

import sth.exceptions.BadEntryException;
import sth.exceptions.DisciplineNotFoundException;
import sth.exceptions.DuplicatePersonException;
import sth.exceptions.DuplicateCourseException;


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
  private Map<Integer, Student> _students = new TreeMap<Integer, Student>();

  /**
   * Association between id's and professors
   */
  private Map<Integer, Professor> _professors = new TreeMap<Integer, Professor>();

  /**
   * Association between id's and staffers
   */
  private Map<Integer, Staffer> _staffers = new TreeMap<Integer, Staffer>();

  /**
   * List of courses
   */
  private List<Course> _courses = new LinkedList<Course>();

  /**
   * Comparator for people
   */
  class PersonComparator implements Comparator<Person>, Serializable {
    public int compare(Person p1, Person p2) { return p1.id() - p2.id(); }
  }
  private Comparator<Person> PersonIdComparator =  new PersonComparator();

  /**
   * Reset the school to its original settings
   */
  private void reset() {
    _currentId = 100000;
    _students = new TreeMap<Integer, Student>();
    _professors = new TreeMap<Integer, Professor>();
    _staffers = new TreeMap<Integer, Staffer>();
    _courses = new LinkedList<Course>();
  }

  /**
   * Add a student to the school
   *
   * @param Student
   * @return Student: allow for `Student s = school.addStudent(new Student(...)) constructs`
   * @throws DuplicatePersonException
   */
  public Student addStudent(Student s) 
    throws DuplicatePersonException {
    if (lookupId(s.id())) throw new DuplicatePersonException();
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
  public Professor addProfessor(Professor p) 
    throws DuplicatePersonException {
    if (lookupId(p.id())) throw new DuplicatePersonException();
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
  public Staffer addStaffer(Staffer s) 
    throws DuplicatePersonException {
    if (lookupId(s.id())) throw new DuplicatePersonException();
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
  public Course addCourse(Course c) 
    throws DuplicateCourseException {
    if (hasCourse(c.name())) throw new DuplicateCourseException();
    _courses.add(c);
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
  public boolean lookupId(int id) {
    return _students.containsKey(id) || _professors.containsKey(id) || _staffers.containsKey(id);
  }
  
  /**
   * Check if an id corresponds to a staffer or not
   *
   * @param id 
   * @return whether an id is a staffer's id
   */
  public boolean isAdministrative(int id) {
    return _staffers.containsKey(id);
  }

  /**
   * Check if an id corresponds to a professor or not
   *
   * @param id 
   * @return whether an id is a professor's id
   */
  public boolean isProfessor(int id) {
    return _professors.containsKey(id);
  }

  /**
   * Check if an id corresponds to a student or not
   *
   * @param id 
   * @return whether an id is a student's id
   */
  public boolean isStudent(int id) {
    return _students.containsKey(id);
  }

  /**
   * Check if an id corresponds to a representative or not
   *
   * @param id 
   * @return whether an id is a representative's id
   */
  public boolean isRepresentative(int id) {
    if (!isStudent(id)) return false;
    for (Course c : _courses)
      if (c.hasRepresentative(id)) return true;

    return false;
  }

  /**
   * Get all the people, ordered by id
   *
   * @return Collection of people
   */
  public Collection<Person> people() {
    Collection<Person> allPeople = new TreeSet<Person>(PersonIdComparator);

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
  public Person getPersonById(int id) {
    Person p;
    p = getStafferById(id);
    if (p != null) return p;

    p = getStudentById(id);
    if (p != null) return p;

    p = getProfessorById(id);
    return p;
  }

  /**
   * Get a staffer by their id
   *
   * @return Staffer
   */
  public Staffer getStafferById(int id) {
    return _staffers.get(id);
  }

  /**
   * Get a student by their id
   *
   * @return Student
   */
  public Student getStudentById(int id) {
    return _students.get(id);
  }

  /**
   * Get a professor by their id
   *
   * @return Professor
   */
  public Professor getProfessorById(int id) {
    return _professors.get(id);
  }

  /**
   * Check whether a school has a course
   *
   * @return boolean
   */
  boolean hasCourse(String name) {
    for (Course c : _courses) {
      if (c.name().equals(name))
        return true;
    }

    return false;
  }

  /**
   * Get a course by its name
   *
   * @return Course
   */
  Course getCourseByName(String name) {
    for (Course c : _courses) {
      if (c.name().equals(name))
        return c;
    }

    return null;
  }

  /**
   * Get people by a common name
   *
   * @return Collection of People
   */
  public Collection<Person> getPersonByName(String name) {
    Collection<Person> result = new ArrayList<Person>();

    for (Person p : people()) {
      if (p.name().contains(name))
        result.add(p);
    }



    return result;
  }

  /**
   * Get a discipline by name
   * Note that this returns the first one (in the event of multiple courses w/ the same Discipline name
   *
   * @return Discipline
   */
  public Discipline getDiscipline(String name) {
    for (Course c : _courses)
      if (c.hasDiscipline(name))
        return c.getDiscipline(name); 
    
    return null;
  }

  /**
   * Get a project by name
   *
   * @return Project
   */
  public Project getProject(String disciplineName, String projectName) throws DisciplineNotFoundException {
    boolean foundDiscipline = false;
    for (Course c : _courses) {
      if (c.hasDiscipline(disciplineName)) {
        foundDiscipline = true;
        Discipline d = c.getDiscipline(disciplineName);
        if (d.hasProject(projectName)) 
          return d.getProject(projectName);
      }
    }

    if (!foundDiscipline) throw new DisciplineNotFoundException(disciplineName);
    return null;
  }
}
